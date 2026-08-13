package com.nixon.cinema.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public record MovieResponse(
        Long id,
        String title,
        String description,
        String ageRating,
        int duration,
        List<String> production,
        List<String> director,
        List<String> mainCast,
        OffsetDateTime releaseDate
) {
}
