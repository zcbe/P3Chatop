package com.zcbe.chatop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zcbe.chatop.dto.MessageDtoRequest;
import com.zcbe.chatop.dto.MessageDtoResponse;
import com.zcbe.chatop.service.JwtService;
import com.zcbe.chatop.service.MessageService;

@RestController
public class MessageController {

    // Injection des services nécessaires : JwtService pour la gestion des tokens et MessageService pour la gestion des messages
    @Autowired
    private JwtService jwtService;

    @Autowired
    private MessageService messageService;

    // Gestion de la création d'un message via une requête POST à l'endpoint /messages
    @PostMapping("/messages")
    public ResponseEntity<MessageDtoResponse> createMessage(
            @RequestBody MessageDtoRequest message,  // Le corps de la requête doit contenir un MessageDtoRequest, qui représente les données du message
            @RequestHeader("Authorization") String token) throws IOException {  // Le token JWT est attendu dans l'en-tête Authorization

        // Validation du token JWT pour s'assurer que l'utilisateur est authentifié
        jwtService.validateToken(token);

        // Appel du service pour créer un message et retour d'une réponse HTTP 201 Created avec le message créé
        return new ResponseEntity<>(messageService.creatMessage(message), HttpStatus.CREATED);
    }
}
