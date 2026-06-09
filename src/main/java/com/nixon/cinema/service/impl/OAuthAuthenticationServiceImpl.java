package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.UserRequest;
import com.nixon.cinema.dto.response.TokenResponse;
import com.nixon.cinema.model.User;
import com.nixon.cinema.repository.UserRepository;
import com.nixon.cinema.service.JwtService;
import com.nixon.cinema.service.OAuthAuthenticationService;
import com.nixon.cinema.service.RefreshTokenService;
import com.nixon.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OAuthAuthenticationServiceImpl implements OAuthAuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Override
    public TokenResponse validateOauthLogin(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String familyName = oAuth2User.getAttribute("family_name");
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return new TokenResponse(
                    jwtService.generateToken(user.get()),
                    refreshTokenService.createRefreshToken(user.get().getUsername()).getToken());
        }

        assert name != null;
        userService.createUser(new UserRequest(name.replaceAll(" ", "").toLowerCase(),
                new Random().doubles().toString(),
                name,
                familyName,
                email,
                null));

        User savedUser = repository.findByEmail(email).get();

        return new TokenResponse(
                jwtService.generateToken(savedUser),
                refreshTokenService.createRefreshToken(savedUser.getUsername()).getToken());


    }
}
