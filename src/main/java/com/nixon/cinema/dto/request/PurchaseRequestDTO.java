package com.nixon.cinema.dto.request;

import com.nixon.cinema.model.enums.PurchaseStatus;

import java.util.List;

public record PurchaseRequestDTO(String description, List<TicketPurchaseRequestDTO> tickets) {
}
