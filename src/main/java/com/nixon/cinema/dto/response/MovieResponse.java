package com.nixon.cinema.dto.response;

import java.time.OffsetDateTime;
import java.util.Set;

public record MovieResponse(
        Long id,
        String title,
        String description,
        String ageRating,
        int duration,
        Set<String> production,
        Set<String> director,
        Set<String> mainCast,
        OffsetDateTime releaseDate
) {
}
