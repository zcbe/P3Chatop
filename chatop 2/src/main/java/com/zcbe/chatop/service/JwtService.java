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

    // Inject JwtEncoder and JwtDecoder via the constructor
    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    // Generate a JWT from authentication information
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();  // Get current time
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:4200")  // Define the issuer of the JWT
                .issuedAt(now)  // Define the creation date of the JWT
                .expiresAt(now.plus(1, ChronoUnit.DAYS))  // Define the expiration date of the JWT (1 day after creation)
                .subject(authentication.getPrincipal().toString())  // Define the subject of the JWT (user identifier)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        // Encode the JWT with the specified header and claims
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();  // Return the encoded token as a string
    }

    // Validate a JWT by decoding it
    public void validateToken(String bearerToken) {
        try {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);  // Extract the token without the "Bearer " prefix
                jwtDecoder.decode(token);  // Decode the token
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");  // Throw an exception if the token is invalid
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", e);  // Throw an exception for illegal argument
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", e);  // Throw an exception for runtime error
        }
    }

    // Get the subject (subject) from the JWT
    public String getSubjectFromToken(String bearerToken) {
        try {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);  // Extract the token without the "Bearer " prefix
                Jwt jwt = jwtDecoder.decode(token);  // Decode the token
                return jwt.getClaims().get("sub").toString();  // Get the subject (user identifier) from the JWT claims
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");  // Throw an exception if the token is invalid
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", e);  // Throw an exception for illegal argument
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", e);  // Throw an exception for runtime error
        }
    }
}
