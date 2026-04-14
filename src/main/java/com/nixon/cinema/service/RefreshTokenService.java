package com.nixon.cinema.service;

import com.nixon.cinema.model.RefreshToken;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);

    boolean isTokenExpired(RefreshToken refreshToken);

    String generateNewToken(String refreshToken);

    String logout(String refreshToken);
}
