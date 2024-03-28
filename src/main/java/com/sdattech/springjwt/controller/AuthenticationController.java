package com.sdattech.springjwt.controller;

import com.sdattech.springjwt.dto.AuthenticationRequest;
import com.sdattech.springjwt.dto.AuthenticationResponse;
import com.sdattech.springjwt.dto.RegistrationResponse;
import com.sdattech.springjwt.model.User;
import com.sdattech.springjwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }


}
