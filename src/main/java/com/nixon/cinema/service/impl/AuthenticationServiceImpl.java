package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.AuthenticationRequest;
import com.nixon.cinema.dto.response.TokenResponse;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.User;
import com.nixon.cinema.repository.UserRepository;
import com.nixon.cinema.service.AuthenticationService;
import com.nixon.cinema.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse login(AuthenticationRequest request) {
        User user = repository.findByUsername(request.username()).orElseThrow(
                () -> new EntityNotFoundException("Username not found")
        );
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );


        return jwtService.generateToken(user);
    }
}
