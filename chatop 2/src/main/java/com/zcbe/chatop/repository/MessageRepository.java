package com.zcbe.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zcbe.chatop.model.MessageModel;

@Repository  // Indique que cette interface est un composant Spring et sera gérée par le conteneur Spring
public interface MessageRepository extends CrudRepository<MessageModel, Long> {
    // Cette interface étend CrudRepository pour bénéficier des méthodes CRUD (Create, Read, Update, Delete) prédéfinies
    // Le paramètre MessageModel indique le type de l'entité que le repository gère
    // Le paramètre Long indique le type de la clé primaire de l'entité
}
