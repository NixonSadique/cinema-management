package com.nixon.cinema.controller;

import com.nixon.cinema.dto.response.TicketResponseDTO;
import com.nixon.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/v1/ticket")
public class TicketController {

    private final TicketService service;

    @GetMapping("/{purchaseId}/")
    ResponseEntity<List<TicketResponseDTO>> getTicketsByPurchaseId(Long purchaseId) {
        return new ResponseEntity<>(service.getTicketByPurchaseId(purchaseId), HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<TicketResponseDTO>> getAllTickets() {
        return new ResponseEntity<>(service.getAllTickets(), HttpStatus.OK);
    }
}
