package com.zcbe.chatop.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zcbe.chatop.model.RentalsModel;

@Repository
public interface RentalsRepository extends CrudRepository<RentalsModel, Long> {
}