package com.zcbe.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zcbe.chatop.model.UserModel;

@Repository  // Indique que cette interface est un composant Spring géré par le conteneur Spring
public interface UserRepository extends CrudRepository<UserModel, Long> {

    // Méthode personnalisée pour trouver un utilisateur par son adresse email
    public UserModel findByEmail(String email);
}
