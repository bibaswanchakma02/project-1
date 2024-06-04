package com.example.authserv.Service;

import com.example.authserv.config.AuthenticationResponse;
import com.example.authserv.model.UserEntity;
import com.example.authserv.repository.UserRepository;
import com.example.authserv.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserEntity request){
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(request.getRoles());

        user = userRepository.save(user);

        String jwt = jwtUtil.generateToken(user);

        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse authenticate(UserEntity request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtUtil.generateToken(user);

        return new AuthenticationResponse(jwt);
    }
}
