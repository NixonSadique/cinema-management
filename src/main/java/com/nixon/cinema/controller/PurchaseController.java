package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.PurchaseRequest;
import com.nixon.cinema.dto.response.PurchaseResponse;
import com.nixon.cinema.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/v1")
@Tag(name = "3.Purchase Controller", description = "Contains the endpoints for the purchases, including creation, cancelation, and confirmation!")
public class PurchaseController {

    private final PurchaseService service;

    @PostMapping("/purchases")
    @Operation(
            summary = "Starts the purchase operation!",
            description = "Create a purchase with the tickets for the chosen movies, showtimes and seats assignment." +
                    " This purchase is created with a PENDING status."
    )
    ResponseEntity<PurchaseResponse> startPurchase(@RequestBody PurchaseRequest request) {
        return new ResponseEntity<>(service.startPurchase(request), CREATED);
    }

    @PutMapping("/purchases/{id}/confirm")
    ResponseEntity<PurchaseResponse> confirmPurchase(@PathVariable Long id) {
        return new ResponseEntity<>(service.confirmPurchase(id), OK);
    }

    @PutMapping("/purchases/{id}/cancel")
    ResponseEntity<String> cancelPurchase(@PathVariable Long id) {
        return new ResponseEntity<>(service.cancelPurchase(id), OK);
    }
}
