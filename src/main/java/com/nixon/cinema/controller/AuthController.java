package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.AuthenticationRequest;
import com.nixon.cinema.dto.request.RefreshTokenAuthRequest;
import com.nixon.cinema.dto.response.TokenResponse;
import com.nixon.cinema.service.AuthenticationService;
import com.nixon.cinema.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    private final RefreshTokenService refreshTokenService;

    @Operation(
            summary = "Log In",
            description = "Endpoint to authenticate a user."
    )
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(service.login(request), HttpStatus.OK);
    }

    @Operation(
            summary = "Refresh",
            description = "Create a new RefreshToken"
    )
    @PostMapping("/refresh")
    ResponseEntity<?> generateNewRefreshToken(@Valid @RequestBody RefreshTokenAuthRequest request) {
        return new ResponseEntity<>(refreshTokenService.generateNewToken(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenAuthRequest request) {
        return new ResponseEntity<>(refreshTokenService.logout(request), HttpStatus.OK);
    }


}
