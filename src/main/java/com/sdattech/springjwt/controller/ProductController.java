package com.sdattech.springjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<String> getProductApi(){
        return ResponseEntity.ok("You have accessed");
    }
}
