package com.nixon.cinema.dto.response;

import com.nixon.cinema.model.enums.PurchaseStatus;

import java.util.List;

public record PurchaseResponseDTO(Long purchaseId, Double price, PurchaseStatus status,
                                  List<TicketResponseDTO> tickets) {
}
