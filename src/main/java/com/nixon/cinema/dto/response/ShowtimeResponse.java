package com.nixon.cinema.dto.response;

import java.time.OffsetDateTime;

public record ShowtimeResponse(
        Long id,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        Double price,
        MovieResponse movie,
        SimpleRoomResponse room
) {
}
