package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.RefreshTokenAuthRequest;
import com.nixon.cinema.dto.response.TokenResponse;
import com.nixon.cinema.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);

    boolean isTokenExpired(RefreshToken refreshToken);

    TokenResponse generateNewToken(RefreshTokenAuthRequest request);

    String logout(RefreshTokenAuthRequest request);
}
