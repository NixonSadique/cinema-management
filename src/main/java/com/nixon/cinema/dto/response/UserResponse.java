package com.nixon.cinema.dto.response;

import com.nixon.cinema.model.enums.Role;

public record UserResponse(
        String username,
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
