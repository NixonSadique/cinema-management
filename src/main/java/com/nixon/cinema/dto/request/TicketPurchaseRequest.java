package com.nixon.cinema.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketPurchaseRequest(
        @Positive @NotNull @Min(1) Long showtimeId,
        @Positive @NotNull @Min(1) Long seatId
) {
}
