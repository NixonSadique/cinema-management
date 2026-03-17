package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.response.TicketResponseDTO;
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
    public List<TicketResponseDTO> getTicketByPurchaseId(Long purchaseId) {
        return ticketRepository.findByPurchaseId(purchaseId).stream().map(
                ticket -> new TicketResponseDTO()
        ).toList();
    }

    @Override
    public List<TicketResponseDTO> getTicketBySeatIdAndShowtimeId(Long seatId, Long showtimeId) {
        return ticketRepository.findBySeatIdAndShowtimeId(seatId, showtimeId).stream().map(
                ticket -> new TicketResponseDTO()
        ).toList();
    }

    @Override
    public List<TicketResponseDTO> getAllTickets() {
        return ticketRepository.findAll().stream().map(
                ticket -> new TicketResponseDTO()
        ).toList();
    }

    @Override
    public TicketResponseDTO getTicketById(Long ticketId) {
        var ticket = ticketRepository.findById(ticketId).orElseThrow();

        return new TicketResponseDTO();
    }

    @Override
    public List<TicketResponseDTO> getAllTicketsByDate(LocalDate date) {
        return List.of();
    }
}
