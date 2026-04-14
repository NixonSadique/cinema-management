package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public record MovieCreationRequest(
        @NotBlank String title,
        @NotBlank String description,
        String ageRating,
        @Positive int duration,
        List<String> production,
        List<String> director,
        List<String> mainCast,
        OffsetDateTime releaseDate
) {
}
