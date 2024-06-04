package com.example.authserv.Controllers;

import com.example.authserv.Service.AuthenticationService;
import com.example.authserv.config.AuthenticationRequest;
import com.example.authserv.config.AuthenticationResponse;
import com.example.authserv.model.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class Authentication {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationRequest.class.getName());

    private final AuthenticationService authenticationService;

    public Authentication(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody UserEntity request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
            @RequestBody UserEntity request
            ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }



}
