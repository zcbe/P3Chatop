package com.zcbe.chatop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Indique que cette classe contient des beans de configuration pour Spring
public class CorsConfig {

    @Bean  // Indique que cette méthode crée un bean géré par le conteneur Spring
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            // Surcharge de la méthode addCorsMappings pour configurer les règles CORS
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Applique la configuration CORS à tous les chemins d'URL de l'application
                        .allowedOrigins("http://localhost:4200")  // Autorise les requêtes provenant de l'origine spécifiée front-end
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Spécifie les méthodes HTTP autorisées pour les requêtes cross-origin
                        .allowedHeaders("*")  // Autorise tous les headers dans les requêtes cross-origin
                        .allowCredentials(true);  // Permet l'envoi des cookies d'authentification ou autres informations d'authentification
            }
        };
    }
}
