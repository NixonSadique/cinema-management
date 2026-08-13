package com.nixon.cinema.service;

import com.nixon.cinema.dto.response.TicketResponse;

import java.util.List;

public interface TicketService {
    List<TicketResponse> getTicketByPurchaseId(Long purchaseId);

    List<TicketResponse> getTicketBySeatIdAndShowtimeId(Long seatId, Long showtimeId);

    List<TicketResponse> getAllTickets();

    TicketResponse getTicketById(Long ticketId);

}
