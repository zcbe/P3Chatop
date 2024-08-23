package com.zcbe.chatop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zcbe.chatop.dto.MessageDtoResponse;
import com.zcbe.chatop.dto.RentalsListDto;
import com.zcbe.chatop.model.RentalsModel;
import com.zcbe.chatop.service.JwtService;
import com.zcbe.chatop.service.RentalsService;

@RestController

public class RentalsController {

    // Injection des services nécessaires : JwtService pour la gestion des tokens JWT et RentalsService pour la gestion des locations
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RentalsService rentalsService;

    // Gestion des requêtes GET à l'endpoint /rentals pour récupérer toutes les locations
    @GetMapping("/rentals")
    public ResponseEntity<RentalsListDto> getAllRentals(@RequestHeader("Authorization") String token) {
        // Validation du token JWT pour s'assurer que l'utilisateur est authentifié
        jwtService.validateToken(token);
        // Retourne la liste des locations avec un statut HTTP 200 OK
        return new ResponseEntity<>(rentalsService.getAllRentals(), HttpStatus.OK);
    }

    // Gestion des requêtes GET à l'endpoint /rentals/{id} pour récupérer une location spécifique par son ID
    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalsModel> getRental(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        // Validation du token JWT pour s'assurer que l'utilisateur est authentifié
        jwtService.validateToken(token);
        // Retourne la location correspondant à l'ID fourni avec un statut HTTP 200 OK
        return new ResponseEntity<>(rentalsService.getRental(id), HttpStatus.OK);
    }

    // Gestion des requêtes POST à l'endpoint /rentals pour créer une nouvelle location
    @PostMapping("/rentals")
    public ResponseEntity<MessageDtoResponse> createRental(
            @RequestParam("name") String name,            
            @RequestParam("surface") Long surface,     
            @RequestParam("price") Long price,      
            @RequestPart("picture") MultipartFile picture,  
            @RequestParam("description") String description,  
            @RequestHeader("Authorization") String token) throws IOException {  

        // Création de la location en utilisant le service et retour d'une réponse HTTP 201 Created avec un message de succès
        return new ResponseEntity<>(rentalsService.createRental(name, surface, price, description, token, picture), HttpStatus.CREATED);
    }

    // Gestion des requêtes PUT à l'endpoint /rentals/{id} pour mettre à jour une location existante
    @PutMapping("/rentals/{id}")
    public ResponseEntity<MessageDtoResponse> updateRental(
            @PathVariable("id") Long id,                  
            @RequestParam("name") String name,            
            @RequestParam("surface") Long surface,        
            @RequestParam("price") Long price,           
            @RequestParam("description") String description, 
            @RequestHeader("Authorization") String token) throws IOException {  

        // Validation du token JWT pour s'assurer que l'utilisateur est authentifié
        jwtService.validateToken(token);
        // Mise à jour de la location en utilisant le service et retour d'une réponse HTTP 200 OK avec un message de succès
        return new ResponseEntity<>(rentalsService.updateRental(id, name, surface, price, description, token), HttpStatus.OK);
    }
}
