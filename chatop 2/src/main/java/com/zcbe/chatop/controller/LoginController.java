package com.zcbe.chatop.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

public class LoginController {

    // Déclaration des services utilisés par le contrôleur
    private final JwtService jwtService;
    private final UserService userService;

    // Injection des dépendances via le constructeur
    @Autowired
    public LoginController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    // Méthode pour gérer la connexion des utilisateurs
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto user) {
        // Affiche les informations de l'utilisateur pour le débogage
        System.out.println(user.toString());
        
        // Récupère l'utilisateur existant à partir de son email
        UserModel existingUser = userService.getUserByEmail(user.getEmail());
        
        // Vérifie si l'utilisateur existe et si le mot de passe est correct
        if (existingUser != null && userService.checkPassword(user, existingUser)) {
            // Crée un objet Authentication avec l'email de l'utilisateur et une liste de rôles vide
            Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getEmail(), null, new ArrayList<>());
            
            // Génère un token JWT pour l'utilisateur authentifié
            String token = jwtService.generateToken(authentication);
            
            // Crée un DTO pour renvoyer le token au client
            TokenDto tokenDto = new TokenDto(token);
            
            // Retourne une réponse 200 OK avec le token
            return ResponseEntity.ok(tokenDto);
        } else {
            // Retourne une réponse 401 Unauthorized si l'authentification échoue
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }
    }

    // Méthode pour gérer l'inscription des nouveaux utilisateurs
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateDto user) {
        // Affiche les informations de l'utilisateur pour le débogage
        System.out.println(user.toString());
        
        // Vérifie si un utilisateur avec le même email existe déjà
        UserModel existingUser = userService.getUserByEmail(user.getEmail());
        
        // Si l'utilisateur n'existe pas, on le crée
        if (existingUser == null) {
            // Crée un nouvel utilisateur dans la base de données
            UserModel newUser = userService.createUser(user);
            
            // Crée un objet Authentication pour le nouvel utilisateur
            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(), null, new ArrayList<>());
            
            // Génère un token JWT pour le nouvel utilisateur
            String token = jwtService.generateToken(authentication);
            
            // Crée un DTO pour renvoyer le token au client
            TokenDto tokenDto = new TokenDto(token);
            
            // Retourne une réponse 200 OK avec le token
            return ResponseEntity.ok(tokenDto);
        } else {
            // Retourne une réponse 400 Conflict si un utilisateur avec le même email existe déjà
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Un utilisateur avec cet email existe déjà");
        }
    }

    // Méthode pour récupérer les informations de l'utilisateur actuellement connecté
    @GetMapping("/auth/me")
    public ResponseEntity<?> me(Authentication authentication) {
        // Récupère les informations de l'utilisateur connecté à partir de l'email (ou du nom d'utilisateur)
        UserModel currentUser = userService.getUserByEmail(authentication.getName());
        
        // Retourne une réponse 200 OK avec les informations de l'utilisateur
        return ResponseEntity.ok(currentUser);
    }

     // Nouvelle méthode pour récupérer les informations d'un utilisateur par son ID
     @GetMapping("/user/{id}")
     public ResponseEntity<?> getUserById(@PathVariable Long id) {
         UserModel user = userService.getUserById(id);
         
         if (user != null) {
             return ResponseEntity.ok(user);
         } else {
            // Retourne une réponse 401 utilisateur non trouvé
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
         }
     }
}
