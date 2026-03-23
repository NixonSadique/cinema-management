package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.response.TicketResponse;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.repository.TicketRepository;
import com.nixon.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<TicketResponse> getTicketByPurchaseId(Long purchaseId) {
        return ticketRepository.findByPurchaseId(purchaseId).stream().map(
                ticket -> new TicketResponse(ticket.getId(),
                        ticket.getUnitPrice(),
                        ticket.getSeat().getSeatRow() + ticket.getSeat().getSeatNumber())
        ).toList();
    }

    @Override
    public List<TicketResponse> getTicketBySeatIdAndShowtimeId(Long seatId, Long showtimeId) {
        return ticketRepository.findBySeatIdAndShowtimeId(seatId, showtimeId).stream().map(
                ticket -> new TicketResponse(ticket.getId(),
                        ticket.getUnitPrice(),
                        ticket.getSeat().getSeatRow() + ticket.getSeat().getSeatNumber())
        ).toList();
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream().map(
                ticket -> new TicketResponse(ticket.getId(),
                        ticket.getUnitPrice(),
                        ticket.getSeat().getSeatRow() + ticket.getSeat().getSeatNumber())
        ).toList();
    }

    @Override
    public TicketResponse getTicketById(Long ticketId) {
        var ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new EntityNotFoundException("Ticket not found")
        );

        return new TicketResponse(ticket.getId(),
                ticket.getUnitPrice(),
                ticket.getSeat().getSeatRow() + ticket.getSeat().getSeatNumber());
    }

    @Override
    public List<TicketResponse> getAllTicketsByDate(LocalDate date) {
        return List.of();
    }
}
