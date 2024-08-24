package com.zcbe.chatop.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service  
public class JwtService {
    private final JwtEncoder jwtEncoder; 
    private final JwtDecoder jwtDecoder; 

    // Injecter JwtEncoder et JwtDecoder via le constructeur
    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    // Générer un JWT à partir des informations d'authentification
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();  // Obtenir l'heure actuelle
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:4200")  // Définir l'émetteur du JWT
                .issuedAt(now)  // Définir la date de création du JWT
                .expiresAt(now.plus(1, ChronoUnit.DAYS))  // Définir la date d'expiration du JWT (1 jour après la création)
                .subject(authentication.getPrincipal().toString())  // Définir le sujet du JWT (identifiant de l'utilisateur)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        // Encoder le JWT avec l'en-tête et les revendications spécifiés
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();  // Retourner le token encodé sous forme de chaîne
    }

    // Valider un JWT en le décodant
    public void validateToken(String bearerToken) {
        try {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);  // Extraire le token sans le préfixe "Bearer "
                jwtDecoder.decode(token);  // Décoder le token
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");  // Lever une exception si le token est invalide
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", e);  // Lever une exception pour argument illégal
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", e);  // Lever une exception pour erreur d'exécution
        }
    }

    // Obtenir le sujet (subject) à partir du JWT
    public String getSubjectFromToken(String bearerToken) {
        try {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);  // Extraire le token sans le préfixe "Bearer "
                Jwt jwt = jwtDecoder.decode(token);  // Décoder le token
                return jwt.getClaims().get("sub").toString();  // Obtenir le sujet (identifiant de l'utilisateur) à partir des revendications du JWT
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");  // Lever une exception si le token est invalide
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", e);  // Lever une exception pour argument illégal
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", e);  // Lever une exception pour erreur d'exécution
        }
    }
}
