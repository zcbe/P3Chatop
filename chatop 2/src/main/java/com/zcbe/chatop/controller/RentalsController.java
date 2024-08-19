package com.zcbe.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private RentalsService rentalService;
    //@Autowired
   // private ModelMapper modelMapper;

    @GetMapping("/rentals")
    public ResponseEntity<RentalsListDto> getAllRentals(@RequestHeader("Authorization") String token) {
        jwtService.validateToken(token);
        return new ResponseEntity<>(rentalService.getAllRentals(), HttpStatus.OK);
    }

    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalsModel> getRental(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        jwtService.validateToken(token);
        return new ResponseEntity<>(rentalService.getRental(id), HttpStatus.OK);
    }

    @PostMapping("/rental/")
    public ResponseEntity<RentalsModel> createRental(@RequestBody RentalsModel rental, @RequestParam Long owner_id, @RequestParam String pathPicture, @RequestHeader("Authorization") String token) {
        jwtService.validateToken(token);
        return new ResponseEntity<>(rentalService.createRental(rental, owner_id, pathPicture), HttpStatus.CREATED);
    }

    @PutMapping("/rental/{id}")
    public ResponseEntity<RentalsModel> updateRental(@PathVariable Long id, @RequestParam Long ownerId, @RequestBody RentalsModel rental, @RequestHeader("Authorization") String token) {
        jwtService.validateToken(token);
        return new ResponseEntity<>(rentalService.updateRental(id, ownerId, rental), HttpStatus.OK);
    }
}