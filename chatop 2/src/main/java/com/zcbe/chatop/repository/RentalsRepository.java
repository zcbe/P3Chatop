package com.zcbe.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zcbe.chatop.model.RentalsModel;

@Repository  // Indique que cette interface est un composant Spring géré par le conteneur Spring
public interface RentalsRepository extends CrudRepository<RentalsModel, Long> {
    // Cette interface étend CrudRepository pour fournir des méthodes CRUD (Create, Read, Update, Delete) prédéfinies
    // Le paramètre RentalsModel indique le type d'entité que le repository gère
    // Le paramètre Long indique le type de la clé primaire de l'entité
}
