package com.zcbe.chatop.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service  
public class JwtService {
    private final JwtEncoder jwtEncoder; 
    private final JwtDecoder jwtDecoder; 

    // Constructeur pour initialiser JwtEncoder et JwtDecoder
    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
        String secret = "29ea3bbd2b985a0ce37620054848c6a6b3b7cd2d3f583013d8e4ba6b744c53fc"; // Clé secrète pour signer le JWT, normalement injectée depuis un fichier de configuration
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");  // Création d'une clé secrète pour HMAC SHA-256
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).build();  // Initialisation du décodeur avec la clé secrète
    }

    // Génère un JWT à partir des informations d'authentification
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();  // Obtient le moment actuel
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:4200")  // Définit l'émetteur du JWT
                .issuedAt(now)  // Définit la date de création du JWT
                .expiresAt(now.plus(1, ChronoUnit.DAYS))  // Définit la date d'expiration du JWT (1 jour après la création)
                .subject(authentication.getPrincipal().toString())  // Définit le sujet du JWT (identifiant de l'utilisateur)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        // Encode le JWT avec l'en-tête et les revendications spécifiés
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();  // Retourne le jeton encodé sous forme de chaîne
    }

    // Valide un JWT en le décodant
    public void validateToken(String bearerToken) {
        try {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);  // Extrait le token sans le préfixe "Bearer "
                jwtDecoder.decode(token);  // Décode le token
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide");  // Lance une exception si le token est invalide
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide", e);  // Lance une exception en cas d'argument illégal
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide", e);  // Lance une exception en cas d'erreur d'exécution
        }
    }

    // Récupère le sujet (subject) du JWT
    public String getSubjectFromToken(String bearerToken) {
        try {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);  // Extrait le token sans le préfixe "Bearer "
                Jwt jwt = jwtDecoder.decode(token);  // Décode le token
                return jwt.getClaims().get("sub").toString();  // Récupère le sujet (identifiant de l'utilisateur) des revendications du JWT
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide");  // Lance une exception si le token est invalide
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide", e);  // Lance une exception en cas d'argument illégal
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide", e);  // Lance une exception en cas d'erreur d'exécution
        }
    }
}
