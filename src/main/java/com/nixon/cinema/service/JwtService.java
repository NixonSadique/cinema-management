package com.nixon.cinema.service;

import com.nixon.cinema.dto.response.TokenResponse;
import com.nixon.cinema.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUsername(String token);

    TokenResponse generateToken(User user);

    boolean isTokenValid(String token, UserDetails userDetails);

    Long extractUserId(String token);

    SimpleGrantedAuthority extractAuthorities(String token);

}
