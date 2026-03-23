package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 6, max = 50) String username,
        @Email() String email,
        @Size(min = 9, max = 9) String phone) {
}
