package com.zcbe.chatop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zcbe.chatop.dto.RentalsListDto;
import com.zcbe.chatop.model.RentalsModel;
import com.zcbe.chatop.service.JwtService;
import com.zcbe.chatop.service.RentalsService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RentalsController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RentalsService rentalsService;


    @GetMapping("/rentals")
    public ResponseEntity<RentalsListDto> getAllRentals(@RequestHeader("Authorization") String token) {
        jwtService.validateToken(token);
        return new ResponseEntity<>(rentalsService.getAllRentals(), HttpStatus.OK);
    }

    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalsModel> getRental(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        jwtService.validateToken(token);
        return new ResponseEntity<>(rentalsService.getRental(id), HttpStatus.OK);
    }

    @PostMapping("/rentals")
    public ResponseEntity<RentalsModel> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Long surface,
            @RequestParam("price") Long price,
            @RequestPart("picture") MultipartFile picture,
            @RequestParam("description") String description,
            @RequestHeader("Authorization") String token) throws IOException {
        return new ResponseEntity<>(rentalsService.createRental(name, surface, price, description, token, picture), HttpStatus.CREATED);
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<RentalsModel> updateRental(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") Long surface,
            @RequestParam("price") Long price,
            @RequestParam("description") String description,
            @RequestHeader("Authorization") String token) throws IOException {    
        jwtService.validateToken(token);
        return new ResponseEntity<>(rentalsService.updateRental(id, name, surface, price, description, token), HttpStatus.OK);

    }
}