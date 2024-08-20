package com.zcbe.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zcbe.chatop.model.MessageModel;

@Repository
public interface MessageRepository extends CrudRepository<MessageModel, Long> {
}