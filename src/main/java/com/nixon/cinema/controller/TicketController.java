package com.nixon.cinema.controller;

import com.nixon.cinema.dto.response.TicketResponse;
import com.nixon.cinema.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/v1")
@Tag(name = "4.Ticket Controller", description = "Endpoints for the tickets retrieval according certain params!")
public class TicketController {

    private final TicketService service;

    @GetMapping("/tickets/{purchaseId}")
    ResponseEntity<List<TicketResponse>> getTicketsByPurchaseId(Long purchaseId) {
        return new ResponseEntity<>(service.getTicketByPurchaseId(purchaseId), HttpStatus.OK);
    }

    @GetMapping("/tickets")
    ResponseEntity<List<TicketResponse>> getAllTickets() {
        return new ResponseEntity<>(service.getAllTickets(), HttpStatus.OK);
    }
}
