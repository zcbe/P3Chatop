package com.zcbe.chatop.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Entity
@Table(name = "rentals")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private Long surface;
    @Column
    private Long price;
    @Column
    private String picture;
    @Column
    private String description;
    @Column
    private Long owner_id;
    @Column
    private Date created_at;
    @Column
    private Date updated_at;
}