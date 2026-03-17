package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.PurchaseRequestDTO;
import com.nixon.cinema.dto.response.PurchaseResponseDTO;
import com.nixon.cinema.dto.response.TicketResponseDTO;
import com.nixon.cinema.dto.response.UserResponseDTO;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.*;
import com.nixon.cinema.model.enums.PurchaseStatus;
import com.nixon.cinema.model.enums.RoomType;
import com.nixon.cinema.repository.*;
import com.nixon.cinema.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public PurchaseResponseDTO makePurchase(PurchaseRequestDTO request) {
        Purchase purchase = new Purchase();
        purchase.setDescription(request.description());

        List<Ticket> tickets = new ArrayList<>();
        Double price = 0.0;

        for (int i = 0; i < request.tickets().size(); i++) {
            Seat seat = seatRepository.findById(request.tickets().get(i).seatId()).orElseThrow(
                    () -> new EntityNotFoundException("Seat Not Found!")
            );
            Showtime showtime = showtimeRepository.findById(request.tickets().get(i).showtimeId()).orElseThrow(
                    () -> new EntityNotFoundException("ShowTime Not Found!")
            );

            if (ticketRepository.existsBySeatIdAndShowtimeId(seat.getId(), showtime.getId()))
                throw new BadRequestException("Seat is occupied already!"); //Make a dedicated Exception for this use case(SeatOccupied)

            Ticket ticket = getTicket(seat, showtime, purchase);
            price += ticket.getUnitPrice();
            tickets.add(ticket);
        }

        purchase.setTickets(tickets);
        purchase.setPrice(price);
        purchase.setStatus(PurchaseStatus.PENDING);
        purchase.setUser(getLoggedUser());
        var savedPurchase = purchaseRepository.save(purchase);

        return new PurchaseResponseDTO(savedPurchase.getId(), savedPurchase.getPrice(), PurchaseStatus.PENDING, tickets.stream().map(
                ticket -> new TicketResponseDTO()
        ).toList());
    }

    /**
     * @return The authenticated User
     * @throws AuthenticationCredentialsNotFoundException if the user isn't authenticated
     */
    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            return userRepository.findByUsername(username).orElseThrow();
        }

        throw new AuthenticationCredentialsNotFoundException("User not found");
    }

    private @NonNull Ticket getTicket(Seat seat, Showtime showtime, Purchase purchase) {

        Ticket ticket = new Ticket();

        ticket.setPurchase(purchase);
        ticket.setPurchaseTime(LocalDateTime.now());
        ticket.setSeat(seat);
        ticket.setShowtime(showtime);


        switch (showtime.getRoom().getRoomType()) {
            case RoomType.ROOM_2D -> ticket.setUnitPrice(500.0);
            case RoomType.ROOM_3D -> ticket.setUnitPrice(650.0);
            case ROOM_4DX -> ticket.setUnitPrice(2500.0);
            case ROOM_IMAX, ROOM_DOLBY_CINEMA -> ticket.setUnitPrice(1000.0);
            case null, default -> throw new BadRequestException("Invalid Room Type");
        }

        return ticket;
    }

    @Override
    public String cancelPurchase(Long purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow(
                () -> new EntityNotFoundException("Purchase Not Found!")
        );

        purchase.setStatus(PurchaseStatus.CANCELLED);

        purchase.getTickets().forEach(ticket -> ticketRepository.deleteById(ticket.getId()));

        return "Purchase Cancelled!";
    }

    @Override
    public PurchaseResponseDTO confirmPurchase(Long purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow(
                () -> new EntityNotFoundException("Purchase Not Found!")
        );

        purchase.setStatus(PurchaseStatus.COMPLETED);


        return new PurchaseResponseDTO(purchase.getId(), purchase.getPrice(), PurchaseStatus.COMPLETED, purchase.getTickets().stream().map(
                ticket -> new TicketResponseDTO()
        ).toList());
    }
}
