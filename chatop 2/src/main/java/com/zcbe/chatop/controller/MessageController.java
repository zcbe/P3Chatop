package com.zcbe.chatop.controller;


import com.zcbe.chatop.dto.MessageDtoRequest;
import com.zcbe.chatop.dto.MessageDtoResponse;
import com.zcbe.chatop.service.JwtService;
import com.zcbe.chatop.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class MessageController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<MessageDtoResponse> createMessage(
            @RequestBody MessageDtoRequest message,
            @RequestHeader("Authorization") String token) throws IOException {
        jwtService.validateToken(token);
        return new ResponseEntity<>(messageService.creatMessage(message), HttpStatus.CREATED);
    }
}
