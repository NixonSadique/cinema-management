package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Size(min = 5, max = 50) @NotBlank String username,
        @Size(min = 5) @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email() String email,
        @Size(min = 9, max = 9) String phone) {
}
