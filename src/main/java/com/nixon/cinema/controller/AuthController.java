package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.AuthenticationRequestDTO;
import com.nixon.cinema.dto.request.UserRequestDTO;
import com.nixon.cinema.dto.response.TokenResponse;
import com.nixon.cinema.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema/v1/auth")
@RequiredArgsConstructor
@Tag(name = "1.Authentication Controller", description = "The endpoint for the user authentication")
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthenticationRequestDTO request) {
        return new ResponseEntity<>(service.login(request), HttpStatus.OK);
    }
}
