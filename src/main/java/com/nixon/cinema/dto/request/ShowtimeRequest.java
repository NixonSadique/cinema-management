package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record ShowtimeRequest(
        @Future OffsetDateTime startTime,
        @Future OffsetDateTime endTime,
        @NotNull @Positive Long roomId,
        @NotNull @Positive Double price
) {
}
