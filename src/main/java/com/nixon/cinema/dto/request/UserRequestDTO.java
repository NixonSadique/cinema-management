package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @Size(min = 6, max = 20) String username,
        @Size(min = 5) String password,
        String firstName,
        String lastName,
        @Email(message = "This field should be an email!") String email,
        String phone) {
}
