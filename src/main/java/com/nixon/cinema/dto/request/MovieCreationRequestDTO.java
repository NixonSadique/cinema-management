package com.nixon.cinema.dto.request;

import java.time.LocalDateTime;
import java.util.Set;

public record MovieCreationRequestDTO(String title, String description, String ageRating, int duration,
                                      Set<String> production, Set<String> director, Set<String> mainCast,
                                      LocalDateTime releaseDate) {
}
