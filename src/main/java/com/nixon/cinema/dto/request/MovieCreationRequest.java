package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;
import java.util.Set;

public record MovieCreationRequest(
        @NotBlank String title,
        @NotBlank String description,
        String ageRating,
        @Positive int duration,
        Set<String> production,
        Set<String> director,
        Set<String> mainCast,
        OffsetDateTime releaseDate
) {
}
