package com.zcbe.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zcbe.chatop.model.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
    public UserModel findByEmail(String email);
}