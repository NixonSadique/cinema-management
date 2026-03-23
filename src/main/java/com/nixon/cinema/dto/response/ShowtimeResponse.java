package com.nixon.cinema.dto.response;

import java.time.LocalDateTime;

public record ShowtimeResponse(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price,
        MovieResponse movie,
        RoomResponseForShowtime room
) {
}
