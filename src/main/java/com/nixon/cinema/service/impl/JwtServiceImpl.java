package com.nixon.cinema.service.impl;

import com.nixon.cinema.model.User;
import com.nixon.cinema.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String secretKey;


    private long expirationTime = 900000L;

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("uId", Long.class);
    }

    @Override
    public SimpleGrantedAuthority extractAuthorities(String token) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String generateToken(User user) {
        log.info("Generating token for user: {}", user.getUsername());
        Date expiresAt = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
                .subject(user.getUsername())
                .claims()
                .add("role", user.getAuthorities())
                .add("uId", user.getId()).and()
                .issuedAt(new Date())
                .expiration(expiresAt)
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.debug("Validating token for user: {}", userDetails.getUsername());
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        Date exp = extractAllClaims(token).getExpiration();
        return (exp.before(new Date()));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
