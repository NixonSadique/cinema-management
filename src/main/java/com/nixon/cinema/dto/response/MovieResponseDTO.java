package com.nixon.cinema.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public record MovieResponseDTO(Long id, String title, String description, String ageRating, int duration,
                               Set<String> production, Set<String> director, Set<String> mainCast,
                               LocalDateTime releaseDate
) {
}
