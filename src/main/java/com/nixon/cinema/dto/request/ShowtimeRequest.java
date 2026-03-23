package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ShowtimeRequest(
        @Future LocalDateTime startTime,
        @Future LocalDateTime endTime,
        @NotNull @Positive Long roomId,
        @NotNull @Positive Double price
) {
}
