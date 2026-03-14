package com.nixon.cinema.service;

import com.nixon.cinema.dto.response.TicketResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface TicketService {
    List<TicketResponseDTO> getTicketByPurchaseId(Long purchaseId);

    List<TicketResponseDTO> getTicketBySeatIdAndShowtimeId(Long seatId, Long showtimeId);

    List<TicketResponseDTO> getAllTickets();

    TicketResponseDTO getTicketById(Long ticketId);

    List<TicketResponseDTO> getAllTicketsByDate(LocalDate date);


}
