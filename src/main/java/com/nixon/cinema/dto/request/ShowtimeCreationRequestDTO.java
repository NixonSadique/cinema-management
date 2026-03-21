package com.nixon.cinema.dto.request;

import java.util.Set;

public record ShowtimeCreationRequestDTO(Set<ShowtimeRequestDTO> showtime, Long movieId) {
}
