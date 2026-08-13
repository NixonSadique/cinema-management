package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.response.TicketResponse;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.Ticket;
import com.nixon.cinema.repository.TicketRepository;
import com.nixon.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private static TicketResponse response(Ticket ticket) {
        String seat = ticket.getSeat().getSeatRow() + ticket.getSeat().getSeatNumber();
        return new TicketResponse(ticket, seat);
    }

    @Override
    @Cacheable(value = "tickets", key = "#purchaseId")
    public List<TicketResponse> getTicketByPurchaseId(Long purchaseId) {
        return ticketRepository.findByPurchaseId(purchaseId).stream().map(
                TicketServiceImpl::response
        ).toList();
    }

    @Override
    @Cacheable(value = "tickets", key = "{#seatId, #showtimeId}")
    public List<TicketResponse> getTicketBySeatIdAndShowtimeId(Long seatId, Long showtimeId) {
        return ticketRepository.findBySeatIdAndShowtimeId(seatId, showtimeId).stream().map(
                TicketServiceImpl::response
        ).toList();
    }

    @Override
    @Cacheable(value = "tickets", key = "'all'")
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream().map(
                TicketServiceImpl::response
        ).toList();
    }

    @Override
    @Cacheable(value = "tickets", key = "#ticketId")
    public TicketResponse getTicketById(Long ticketId) {
        var ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new EntityNotFoundException("Ticket not found")
        );
        return response(ticket);
    }

}
