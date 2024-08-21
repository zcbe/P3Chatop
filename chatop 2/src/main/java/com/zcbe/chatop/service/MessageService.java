package com.zcbe.chatop.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcbe.chatop.dto.MessageDtoRequest;
import com.zcbe.chatop.dto.MessageDtoResponse;
import com.zcbe.chatop.model.MessageModel;
import com.zcbe.chatop.repository.MessageRepository;

@Service  
public class MessageService {

    @Autowired  // Injecte automatiquement une instance de MessageRepository
    private MessageRepository messageRepository;

    // Méthode pour créer un nouveau message
    public MessageDtoResponse creatMessage(MessageDtoRequest messageDtoRequest) {
        // Crée une nouvelle instance de MessageModel
        MessageModel message = new MessageModel();
        
        // Définit les propriétés du modèle de message en utilisant les données du DTO
        message.setMessage(messageDtoRequest.getMessage());
        message.setCreated_at(new Date());  // Définit la date de création du message
        message.setRental_id(messageDtoRequest.getRental_id());  // Associe le message à un identifiant de location
        message.setUser_id(messageDtoRequest.getUser_id());  // Définit l'identifiant de l'utilisateur qui a envoyé le message
        
        // Sauvegarde le modèle de message dans la base de données via le repository
        messageRepository.save(message);
        
        // Crée une réponse DTO contenant le message de confirmation
        MessageDtoResponse messageDtoResponse = new MessageDtoResponse();
        messageDtoResponse.setMessage("Votre message à bien été envoyé : " + messageDtoRequest.getMessage());
        
        // Retourne la réponse DTO
        return messageDtoResponse;
    }
}
