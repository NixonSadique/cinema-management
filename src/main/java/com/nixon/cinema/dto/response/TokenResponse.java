package com.nixon.cinema.dto.response;

import java.util.Date;

public record TokenResponse(
        String token,
        Date expiration
) {
}
