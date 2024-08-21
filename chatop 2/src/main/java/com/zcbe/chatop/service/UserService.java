package com.zcbe.chatop.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zcbe.chatop.dto.UserCreateDto;
import com.zcbe.chatop.dto.UserLoginDto;
import com.zcbe.chatop.model.UserModel;
import com.zcbe.chatop.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository; // Injection du repository pour accéder aux données utilisateur
    
    @Autowired
    private ModelMapper modelMapper; // Injection de ModelMapper pour la conversion DTO <-> modèle
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Injection de BCryptPasswordEncoder pour le hachage des mots de passe

    // Récupère tous les utilisateurs de la base de données
    public Iterable<UserModel> getUsers() {
        return userRepository.findAll();
    }

    // Récupère un utilisateur par son email
    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Crée un nouvel utilisateur à partir des données fournies dans le DTO
    public UserModel createUser(UserCreateDto userDto) {
        UserModel user = modelMapper.map(userDto, UserModel.class); // Convertit le DTO en modèle
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hache le mot de passe
        user.setCreatedAt(new Date()); // Définit la date de création
        return userRepository.save(user); // Sauvegarde le nouvel utilisateur dans la base de données
    }

    // Vérifie si le mot de passe fourni correspond au mot de passe haché stocké dans la base de données
    public boolean checkPassword(UserLoginDto userLoginDto, UserModel user) {
        return passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
    }
}
