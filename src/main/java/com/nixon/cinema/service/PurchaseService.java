package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.PurchaseRequestDTO;
import com.nixon.cinema.dto.response.PurchaseResponseDTO;
import com.nixon.cinema.model.Purchase;

public interface PurchaseService {
    PurchaseResponseDTO makePurchase(PurchaseRequestDTO request);

    String cancelPurchase(Long purchaseId);

    PurchaseResponseDTO confirmPurchase(Long purchaseId);

}
