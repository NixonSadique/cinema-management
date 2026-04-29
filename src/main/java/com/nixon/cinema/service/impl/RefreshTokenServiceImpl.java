package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.RefreshTokenAuthRequest;
import com.nixon.cinema.dto.response.TokenResponse;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.RefreshToken;
import com.nixon.cinema.repository.RefreshTokenRepository;
import com.nixon.cinema.repository.UserRepository;
import com.nixon.cinema.service.JwtService;
import com.nixon.cinema.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User not found!")
        ));
        refreshToken.setExpiryDate(Instant.now().plusMillis(1296000000L));
        refreshToken.setToken(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()));
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isBefore(Instant.now());
    }

    @Override
    public TokenResponse generateNewToken(RefreshTokenAuthRequest request) {
        var requestToken = refreshTokenRepository.findByToken(request.refreshToken()).orElseThrow(
                () -> new EntityNotFoundException("RefreshToken not found!")
        );

        if (isTokenExpired(requestToken)) {
            refreshTokenRepository.delete(requestToken);
            throw new BadRequestException("Token is expired!");
        }

        var user = requestToken.getUser();

        var token = jwtService.generateToken(user);

        return new TokenResponse(token, request.refreshToken());
    }

    @Override
    public String logout(RefreshTokenAuthRequest request) {
        var token = refreshTokenRepository.findByToken(request.refreshToken()).orElseThrow(
                () -> new EntityNotFoundException("RefreshToken not found!")
        );

        refreshTokenRepository.delete(token);
        return "Logout";
    }
}
