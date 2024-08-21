package com.zcbe.chatop.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration  
@EnableWebSecurity  // Active la sécurité Web dans cette application
public class SpringSecurityConfig {

    @Value("${jwt.key}")  // Injecte la clé JWT depuis les propriétés de l'application, signer et verifier les tokens Jwt
    private String jwtKey;

    @Bean
    public JwtEncoder jwtEncoder() {
        // Crée et retourne un JwtEncoder configuré pour encoder des JWT avec la clé secrète
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
        // Crée une clé secrète spécifiée pour décoder les JWT avec l'algorithme HMACSHA256
        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), "HMACSHA256");
        // Retourne un JwtDecoder configuré avec cette clé secrète
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

   

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configure la chaîne de filtres de sécurité
        return http
                .csrf(AbstractHttpConfigurer::disable)  // Désactive la protection CSRF car l'application est sans état (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configure la gestion des sessions pour ne pas créer de session (stateless)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/resources/**",
                                "/api/images/**",
                                "/uploads/**",
                                "/uploads/images",
                                "/static/**",
                                "/images/**",
                                "/auth/**",
                                "/swagger*/**",
                                "/v3/api-docs",
                                "/v3/api-docs/swagger-config"
                                ).permitAll()  
                        .anyRequest().authenticated())  // Exige l'authentification pour toute autre requête
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))  // Configure le serveur de ressources OAuth2 pour utiliser JWT
                .build();  // Construit et retourne la configuration de la chaîne de filtres de sécurité
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Retourne un encodeur de mots de passe utilisant l'algorithme BCrypt
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        // Retourne une instance de ModelMapper, utilisée pour mapper les objets DTO et entités
        return new ModelMapper();
    }
}
