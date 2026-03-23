package com.nixon.cinema.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PurchaseRequest(
        String description,
        @Valid @NotEmpty List<TicketPurchaseRequest> tickets
) {
}
