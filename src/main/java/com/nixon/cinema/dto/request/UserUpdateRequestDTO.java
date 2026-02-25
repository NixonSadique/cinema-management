package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDTO(
        @Size(min = 6, max = 20) String username,
        @Email(message = "This field should be an email!") String email,
        String phone) {
}
