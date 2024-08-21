package com.zcbe.chatop.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure la gestion des ressources statiques pour l'application
        registry.addResourceHandler("/api/images/**")  // Définit le chemin URL à partir duquel les ressources seront accessibles
                .addResourceLocations("file:uploads/");  // Définit le répertoire où les ressources sont stockées sur le disque
    }
}
