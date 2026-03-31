package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Email() @NotBlank String email,
        @Size(min = 9, max = 9) @NotBlank String phone) {
}
