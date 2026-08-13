package com.nixon.cinema.dto.response;

import com.nixon.cinema.model.Ticket;

public record TicketResponse(
        Long id,
        Double unitPrice,
        String seat
) {

    public TicketResponse(Ticket ticket, String seat) {
        this(ticket.getId(), ticket.getUnitPrice(), seat);
    }
}
