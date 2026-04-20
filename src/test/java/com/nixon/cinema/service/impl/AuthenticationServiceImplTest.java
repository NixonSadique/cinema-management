package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.AuthenticationRequest;
import com.nixon.cinema.dto.response.TokenResponse;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.RefreshToken;
import com.nixon.cinema.model.User;
import com.nixon.cinema.repository.UserRepository;
import com.nixon.cinema.service.JwtService;
import com.nixon.cinema.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;
    @Mock
    private RefreshTokenService refreshTokenService;

    @Test
    void login_withValidCredentials_returnsTokenResponse() {
        User user = new User();
        AuthenticationRequest request = new AuthenticationRequest("username", "password");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.username(), request.password());
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refreshToken");

        when(repository.findByUsername(request.username())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        when(jwtService.generateToken(user)).thenReturn("token");
        when(refreshTokenService.createRefreshToken(request.username())).thenReturn(refreshToken);

        TokenResponse tokenResponse = authenticationServiceImpl.login(request);

        assertNotNull(tokenResponse);
        assertEquals("token", tokenResponse.accessToken());
        assertEquals(refreshToken.getToken(), tokenResponse.refreshToken());

        verify(repository).findByUsername(request.username());
        verify(authenticationManager).authenticate(authenticationToken);
        verify(jwtService).generateToken(user);
        verify(refreshTokenService).createRefreshToken(request.username());
    }

    @Test
    void login_withInvalidUsername_throwsException() {
        AuthenticationRequest request = new AuthenticationRequest("username", "password");

        when(repository.findByUsername(request.username())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authenticationServiceImpl.login(request));

        verify(repository).findByUsername(request.username());
        verifyNoInteractions(authenticationManager, jwtService, refreshTokenService);
    }

}