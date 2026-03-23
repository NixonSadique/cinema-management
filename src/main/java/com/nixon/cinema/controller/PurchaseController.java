package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.PurchaseRequestDTO;
import com.nixon.cinema.dto.response.PurchaseResponseDTO;
import com.nixon.cinema.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/v1/purchase")
@Tag(name = "3.Purchase Controller", description = "Contains the endpoints for the purchases, including creation, cancelation, and confirmation!")
public class PurchaseController {

    private final PurchaseService service;

    @PostMapping("/")
    @Operation(
            summary = "Starts the purchase operation!",
            description = "Create a purchase with the tickets for the chosen movies, showtimes and seats assignment." +
                    " This purchase is created with a PENDING status."
    )
    ResponseEntity<PurchaseResponseDTO> startPurchase(@RequestBody PurchaseRequestDTO request) {
        return new ResponseEntity<>(service.startPurchase(request), CREATED);
    }

    @PutMapping("/{purchaseId}/confirm")
    ResponseEntity<PurchaseResponseDTO> confirmPurchase(@PathVariable Long purchaseId) {
        return new ResponseEntity<>(service.confirmPurchase(purchaseId), OK);
    }

    @PutMapping("/{purchaseId}/cancel")
    ResponseEntity<String> cancelPurchase(@PathVariable Long purchaseId) {
        return new ResponseEntity<>(service.cancelPurchase(purchaseId), OK);
    }
}
