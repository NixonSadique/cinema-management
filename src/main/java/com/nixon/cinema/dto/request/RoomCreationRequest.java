package com.nixon.cinema.dto.request;

import com.nixon.cinema.model.enums.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RoomCreationRequest(
        @NotBlank String name,
        @NotBlank RoomType type,
        @Min(50) Integer capacity,
        @Min(5) Integer columnNumber
) {
}
