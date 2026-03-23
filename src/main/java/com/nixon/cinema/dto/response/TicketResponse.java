package com.nixon.cinema.dto.response;

public record TicketResponse(
        Long id,
        Double unitPrice,
        String seat
) {
}
