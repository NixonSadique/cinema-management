package com.nixon.cinema.dto.request;

import java.time.LocalDateTime;

public record ShowtimeRequestDTO(LocalDateTime startTime, LocalDateTime endTime, Long roomId) {
}
