package com.zcbe.chatop.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.zcbe.chatop.dto.MessageDtoResponse;
import com.zcbe.chatop.dto.RentalsDto;
import com.zcbe.chatop.dto.RentalsListDto;
import com.zcbe.chatop.model.RentalsModel;
import com.zcbe.chatop.model.UserModel;
import com.zcbe.chatop.repository.RentalsRepository;
import com.zcbe.chatop.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class RentalsService {
    
    @Autowired
    private RentalsRepository rentalsRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtService jwtService;

    @Value("${aws.accessKeyId}")
    private String awsAccessKeyId;

    @Value("${aws.secretAccessKey}")
    private String awsSecretAccessKey;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private S3Client s3Client;

    // Méthode annotée pour initialiser le client S3 après la construction du bean
    @PostConstruct
    public void init() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
        this.s3Client = S3Client.builder()
                .region(Region.EU_WEST_3)  // Définit la région pour le client S3
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    // Méthode pour récupérer toutes les annonces de location
    public RentalsListDto getAllRentals() {
        Iterable<RentalsModel> rentalsModels = rentalsRepository.findAll();
        List<RentalsDto> rentalsDtos = new ArrayList<>();
        for (RentalsModel rentalsModel : rentalsModels) {
            RentalsDto rentalsDto = modelMapper.map(rentalsModel, RentalsDto.class);
            rentalsDtos.add(rentalsDto);
        }
        RentalsListDto rentalsListDto = new RentalsListDto();
        rentalsListDto.setRentals(rentalsDtos);
        return rentalsListDto;
    }

    // Méthode pour récupérer une annonce spécifique par son identifiant
    public RentalsModel getRental(Long idRental) {
        return rentalsRepository.findById(idRental)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
    }

    // Méthode pour créer une nouvelle annonce de location
    public MessageDtoResponse createRental(String name, Long surface, Long price, String description, String bearerToken, MultipartFile picture ) throws IOException {
        System.out.println("Creating Rental with name: " + name);
        System.out.println("Bearer Token: " + bearerToken);

        // Obtient l'email de l'utilisateur à partir du token JWT
        String userEmail = jwtService.getSubjectFromToken(bearerToken);
        UserModel user = userRepository.findByEmail(userEmail);

        // Crée un nouveau modèle de location
        RentalsModel rental = new RentalsModel();
        String fileUrl = uploadFileToS3(picture);  // Télécharge l'image sur S3 et obtient l'URL
        rental.setPicture(fileUrl);
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setOwner_id(user.getId());
        rental.setCreated_at(new Date());

        System.out.println("Inserting Rental: " + rental);
        MessageDtoResponse messageDtoResponse = new MessageDtoResponse();
        messageDtoResponse.setMessage("Your real estate ad has been created");
        rentalsRepository.save(rental);
        return messageDtoResponse;
    }

    // Méthode pour téléverser un fichier sur AWS S3
    private String uploadFileToS3(MultipartFile file) throws IOException {
        String key = "images/" + file.getOriginalFilename();  // Détermine le chemin du fichier dans le bucket

        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }

        String fileUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toExternalForm();
        System.out.println("Uploaded file URL: " + fileUrl);
        return fileUrl;
    }

    // Méthode pour mettre à jour une annonce de location existante
    public MessageDtoResponse updateRental(Long rentalId, String name, Long surface, Long price, String description, String bearerToken) {
        RentalsModel rentalToUpdate = rentalsRepository.findById(rentalId)
         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        // Obtient l'email de l'utilisateur à partir du token JWT
        String userEmail = jwtService.getSubjectFromToken(bearerToken);
        UserModel user = userRepository.findByEmail(userEmail);

        // Vérifie si l'utilisateur est le propriétaire de la location
        if (user.getId().equals(rentalToUpdate.getOwner_id())) {
            if (name != null) rentalToUpdate.setName(name);
            if (surface != null) rentalToUpdate.setSurface(surface);
            if (price != null) rentalToUpdate.setPrice(price);
            if (description != null) rentalToUpdate.setDescription(description);
            rentalToUpdate.setUpdated_at(new Date());
            rentalsRepository.save(rentalToUpdate);
            MessageDtoResponse messageDtoResponse = new MessageDtoResponse();
            messageDtoResponse.setMessage("Your real estate ad has been updated");
            return messageDtoResponse;
        
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only the owner can update the listing'");
        }
    }
}
