package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.PurchaseRequest;
import com.nixon.cinema.dto.request.TicketPurchaseRequest;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.exceptions.SeatAlreadyBookedException;
import com.nixon.cinema.model.*;
import com.nixon.cinema.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nixon.cinema.model.enums.PurchaseStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private ShowtimeRepository showtimeRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;


    @Test
    void startPurchase_whenSuccessful_returnPurchaseResponse() {
        var ticketRequest = new TicketPurchaseRequest(1L, 1L);
        PurchaseRequest request = new PurchaseRequest(
                "request",
                List.of(ticketRequest)
        );

        Seat seat = new Seat();
        seat.setId(1L);
        seat.setSeatRow("A");
        seat.setSeatNumber(1);

        Showtime showtime = new Showtime();
        showtime.setId(1L);
        showtime.setPrice(100.0);

        User user = new User();
        user.setUsername("username");

        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(ticketRepository.existsBySeatIdAndShowtimeId(1L, 1L)).thenReturn(false);

        when(authentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        var purchase = new Purchase();
        purchase.setId(1L);
        purchase.setUser(user);
        purchase.setStatus(PENDING);
        purchase.setTickets(List.of());
        purchase.setPrice(100.0);

        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        var response = purchaseService.startPurchase(request);

        assertNotNull(response);
        assertEquals(PENDING, response.status());
        assertEquals(1L, response.purchaseId());
        assertEquals(showtime.getPrice(), response.price());
        assertEquals(100.0, response.price());

        verify(seatRepository).findById(1L);
        verify(ticketRepository).existsBySeatIdAndShowtimeId(1L, 1L);
        verify(authentication, times(2)).getPrincipal();
        verify(userRepository).findByUsername("username");
        verify(purchaseRepository).save(any(Purchase.class));
    }

    @Test
    void startPurchase_whenSeatNotFound_throwException() {
        var ticketRequest = new TicketPurchaseRequest(1L, 1L);
        PurchaseRequest request = new PurchaseRequest(
                "request",
                List.of(ticketRequest)
        );

        when(seatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> purchaseService.startPurchase(request));
        verify(seatRepository).findById(1L);
        verifyNoInteractions(showtimeRepository, ticketRepository, userRepository, purchaseRepository);
    }

    @Test
    void startPurchase_whenShowtimeNotFound_throwException() {
        var ticketRequest = new TicketPurchaseRequest(1L, 1L);
        PurchaseRequest request = new PurchaseRequest(
                "request",
                List.of(ticketRequest)
        );

        Seat seat = new Seat();

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));
        when(showtimeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> purchaseService.startPurchase(request));
        verify(seatRepository).findById(1L);
        verify(showtimeRepository).findById(1L);
        verifyNoInteractions(ticketRepository, userRepository, purchaseRepository);
    }

    @Test
    void startPurchase_whenSeatIsOccupied_throwException() {
        var ticketRequest = new TicketPurchaseRequest(1L, 1L);
        PurchaseRequest request = new PurchaseRequest(
                "request",
                List.of(ticketRequest)
        );
        Seat seat = new Seat();
        seat.setId(1L);
        Showtime showtime = new Showtime();
        showtime.setId(1L);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(ticketRepository.existsBySeatIdAndShowtimeId(1L, 1L)).thenReturn(true);

        assertThrows(SeatAlreadyBookedException.class, () -> purchaseService.startPurchase(request));

        verify(seatRepository).findById(1L);
        verify(showtimeRepository).findById(1L);
        verify(ticketRepository).existsBySeatIdAndShowtimeId(1L, 1L);
        verifyNoInteractions(userRepository, purchaseRepository);
    }


    @AfterEach()
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void cancelPurchase_whenSuccessful_returnMessage() {
        var purchaseId = 1L;
        var purchase = new Purchase();
        purchase.setId(purchaseId);
        purchase.setTickets(new ArrayList<>());

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
        when(purchaseRepository.save(purchase)).thenReturn(purchase);

        var response = purchaseService.cancelPurchase(purchaseId);

        assertNotNull(response);
        assertEquals("Purchase Cancelled!", response);

        verify(purchaseRepository).findById(purchaseId);
        verify(purchaseRepository).save(purchase);
    }

    @Test
    void cancelPurchase_whenPurchaseNotFound_throwException() {
        var purchaseId = 1L;

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> purchaseService.cancelPurchase(purchaseId));

        verify(purchaseRepository).findById(purchaseId);
        verify(purchaseRepository, never()).save(any(Purchase.class));
    }

    @Test
    void confirmPurchase_whenSuccessful_returnPurchaseResponse() {
        var purchaseId = 1L;

        Seat seat = new Seat();
        seat.setId(1L);
        seat.setSeatRow("A");
        seat.setSeatNumber(1);

        Showtime showtime = new Showtime();
        showtime.setId(1L);
        showtime.setPrice(100.0);

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUnitPrice(100.0);
        ticket.setSeat(seat);
        ticket.setShowtime(showtime);

        Purchase purchase = new Purchase();
        purchase.setId(1L);
        purchase.setPrice(100.0);
        purchase.setStatus(PENDING);
        purchase.setTickets(List.of(ticket));

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
        when(purchaseRepository.save(purchase)).thenReturn(purchase);

        var response = purchaseService.confirmPurchase(purchaseId);

        assertNotNull(response);
        assertEquals(1L, response.purchaseId());
        assertEquals("A1", response.tickets().get(0).seat());
        assertEquals(100.0, response.price());
        assertEquals(COMPLETED, response.status());
        assertEquals(1, response.tickets().size());

        verify(purchaseRepository).findById(purchaseId);
        verify(purchaseRepository).save(purchase);

    }

    @Test
    void confirmPurchase_whenPurchaseIsCancelled_throwException() {
        var purchaseId = 1L;
        Purchase purchase = new Purchase();
        purchase.setId(1L);
        purchase.setStatus(CANCELLED);

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

        assertThrows(BadRequestException.class, () -> purchaseService.confirmPurchase(purchaseId));

        verify(purchaseRepository).findById(purchaseId);
        verify(purchaseRepository, never()).save(purchase);

    }


}