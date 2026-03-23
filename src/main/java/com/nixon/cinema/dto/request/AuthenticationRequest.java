package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
        @Size(min = 6, max = 50) String username,
        @Size(min = 5) String password) {
}
