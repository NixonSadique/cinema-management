package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.PurchaseRequest;
import com.nixon.cinema.dto.response.PurchaseResponse;
import com.nixon.cinema.dto.response.TicketResponse;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.exceptions.SeatAlreadyBookedException;
import com.nixon.cinema.model.*;
import com.nixon.cinema.model.enums.PurchaseStatus;
import com.nixon.cinema.repository.*;
import com.nixon.cinema.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.nixon.cinema.model.enums.PurchaseStatus.*;
import static com.nixon.cinema.model.enums.PurchaseStatus.CANCELLED;
import static com.nixon.cinema.model.enums.PurchaseStatus.COMPLETED;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public PurchaseResponse startPurchase(PurchaseRequest request) {
        Purchase purchase = new Purchase();
        purchase.setDescription(request.description());

        List<Ticket> tickets = new ArrayList<>();
        Double price = 0.0;

        for (int i = 0; i < request.tickets().size(); i++) {
            Seat seat = seatRepository.findById(request.tickets().get(i).seatId()).orElseThrow(
                    () -> new EntityNotFoundException("Seat Not Found!")
            );
            Showtime showtime = showtimeRepository.findById(request.tickets().get(i).showtimeId()).orElseThrow(
                    () -> new EntityNotFoundException("Showtime Not Found!")
            );

            if (ticketRepository.existsBySeatIdAndShowtimeId(seat.getId(), showtime.getId()))
                throw new SeatAlreadyBookedException("Seat is Occupied, choose another!");

            Ticket ticket = buildTicket(seat, showtime, purchase);
            price += ticket.getUnitPrice();
            tickets.add(ticket);
        }

        purchase.setTickets(tickets);
        purchase.setPrice(price);
        purchase.setStatus(PENDING);
        purchase.setUser(getLoggedUser());
        var savedPurchase = purchaseRepository.save(purchase);

        return new PurchaseResponse(savedPurchase.getId(), savedPurchase.getPrice(), PENDING, tickets.stream().map(
                ticket -> new TicketResponse(ticket.getId(),
                        ticket.getUnitPrice(),
                        ticket.getSeat().getSeatRow() + ticket.getSeat().getSeatNumber())
        ).toList());
    }

    private @NonNull User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            return userRepository.findByUsername(username).orElseThrow();
        }

        throw new EntityNotFoundException("User not found");
    }

    private @NonNull Ticket buildTicket(Seat seat, Showtime showtime, Purchase purchase) {

        Ticket ticket = new Ticket();

        ticket.setPurchase(purchase);
        ticket.setPurchaseTime(OffsetDateTime.now());
        ticket.setSeat(seat);
        ticket.setShowtime(showtime);
        ticket.setUnitPrice(showtime.getPrice());

        return ticket;
    }

    @Override
    public String cancelPurchase(Long purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow(
                () -> new EntityNotFoundException("Purchase Not Found!")
        );

        purchase.setStatus(CANCELLED);

        purchase.getTickets().clear();

        purchaseRepository.save(purchase);

        return "Purchase Cancelled!";
    }

    @Override
    public PurchaseResponse confirmPurchase(Long purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow(
                () -> new EntityNotFoundException("Purchase Not Found!")
        );

        if (purchase.getStatus() == PurchaseStatus.CANCELLED) {
            throw new BadRequestException("Purchase is already cancelled");
        }

        purchase.setStatus(COMPLETED);

        purchaseRepository.save(purchase);

        return new PurchaseResponse(purchase.getId(), purchase.getPrice(), COMPLETED, purchase.getTickets().stream().map(
                ticket -> new TicketResponse(ticket.getId(),
                        ticket.getUnitPrice(),
                        ticket.getSeat().getSeatRow() + ticket.getSeat().getSeatNumber()
                )
        ).toList());
    }
}
