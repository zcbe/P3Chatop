package com.zcbe.chatop.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zcbe.chatop.dto.TokenDto;
import com.zcbe.chatop.dto.UserCreateDto;
import com.zcbe.chatop.dto.UserLoginDto;
import com.zcbe.chatop.model.UserModel;
import com.zcbe.chatop.service.JwtService;
import com.zcbe.chatop.service.UserService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class LoginController {
    private final JwtService jwtService;
    private final UserService userService;
    @Autowired
    public LoginController(JwtService jwtService, UserService userService) {
                this.jwtService = jwtService;
                this.userService = userService;

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto user) {
        System.out.println(user.toString());
        UserModel existingUser = userService.getUserByEmail(user.getEmail());
        if (existingUser != null && userService.checkPassword(user, existingUser)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getEmail(), null, new ArrayList<>());
            String token = jwtService.generateToken(authentication);
            TokenDto tokenDto = new TokenDto(token);
            return ResponseEntity.ok(tokenDto);
                } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }
    }
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateDto user) {
        System.out.println(user.toString());
        UserModel existingUser = userService.getUserByEmail(user.getEmail());
        if (existingUser == null) {
            UserModel newUser = userService.createUser(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(), null, new ArrayList<>());
            String token = jwtService.generateToken(authentication);
            TokenDto tokenDto = new TokenDto(token);
            return ResponseEntity.ok(tokenDto);
                } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Un utilisateur avec cet email existe déjà");
        }
    }

    @GetMapping("/auth/me")
    public ResponseEntity<?> me(Authentication authentication) {
        UserModel currentUser = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(currentUser);
    }

}
