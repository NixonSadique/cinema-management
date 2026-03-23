package com.nixon.cinema.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record ShowtimeCreationRequest(
        @Valid @NotEmpty Set<ShowtimeRequest> showtimes,
        @NotNull @Positive Long movieId
) {
}
