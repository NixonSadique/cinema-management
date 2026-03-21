package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.PurchaseRequestDTO;
import com.nixon.cinema.dto.response.PurchaseResponseDTO;

public interface PurchaseService {
    PurchaseResponseDTO startPurchase(PurchaseRequestDTO request);

    String cancelPurchase(Long purchaseId);

    PurchaseResponseDTO confirmPurchase(Long purchaseId);

}
