package com.nixon.cinema.service;

import com.nixon.cinema.dto.response.TokenResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthAuthenticationService {
    TokenResponse validateOauthLogin(OAuth2User user);
}
