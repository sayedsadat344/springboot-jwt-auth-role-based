package com.sdattech.springjwt.service;

import com.sdattech.springjwt.dto.AuthenticationRequest;
import com.sdattech.springjwt.dto.AuthenticationResponse;
import com.sdattech.springjwt.dto.RegistrationResponse;
import com.sdattech.springjwt.jwt.service.JwtService;
import com.sdattech.springjwt.model.User;
import com.sdattech.springjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;




    public RegistrationResponse registerUser(User userRequest){

        User user = createNewUser(userRequest);
        user = userRepository.save(user);
        return new RegistrationResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                "User created successfully"
        );
    }

    private User createNewUser(User userRequest) {
        User newUser = new User();
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setRole(userRequest.getRole());
        return newUser;
    }


    public AuthenticationResponse login(AuthenticationRequest request){
         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword()));

         User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
         String token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                token
        );
    }
}
