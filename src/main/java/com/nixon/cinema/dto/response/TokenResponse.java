package com.nixon.cinema.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
