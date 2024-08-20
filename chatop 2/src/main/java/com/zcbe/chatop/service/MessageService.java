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
    @Autowired
    private MessageRepository messageRepository;
    public MessageDtoResponse creatMessage(MessageDtoRequest messageDtoRequest) {
        MessageModel message = new MessageModel();
        message.setMessage(messageDtoRequest.getMessage());
        message.setCreated_at(new Date());
        message.setRental_id(messageDtoRequest.getRental_id());
        message.setUser_id(messageDtoRequest.getUser_id());
        messageRepository.save(message);
        MessageDtoResponse messageDtoResponse = new MessageDtoResponse();
        messageDtoResponse.setMessage("Votre message à bien été envoyé : " + messageDtoRequest.getMessage());
        return messageDtoResponse;
    }
}