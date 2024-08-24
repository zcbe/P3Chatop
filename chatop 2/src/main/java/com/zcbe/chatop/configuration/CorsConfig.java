package com.zcbe.chatop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Indique que cette classe est une classe de configuration pour Spring
public class CorsConfig {

    @Bean  // Définit un bean qui sera géré par le conteneur Spring
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            // Surcharge la méthode addCorsMappings pour configurer les règles de CORS
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Applique la configuration CORS à tous les chemins de l'application
                        .allowedOrigins("http://localhost:4200")  // Autorise uniquement les requêtes provenant de l'origine spécifiée
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Définit les méthodes HTTP autorisées pour les requêtes cross-origin
                        .allowedHeaders("*")  // Permet tous les en-têtes dans les requêtes cross-origin
                        .allowCredentials(true);  // Autorise l'envoi de cookies d'authentification avec les requêtes cross-origin
            }
        };
    }
}
