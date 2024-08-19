package com.zcbe.chatop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zcbe.chatop.model.RentalsModel;

@Repository
public interface RentalsRepository extends JpaRepository<RentalsModel, Long> {
}