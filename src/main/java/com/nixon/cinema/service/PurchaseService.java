package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.PurchaseRequest;
import com.nixon.cinema.dto.response.PurchaseResponse;

public interface PurchaseService {
    PurchaseResponse startPurchase(PurchaseRequest request);

    String cancelPurchase(Long purchaseId);

    PurchaseResponse confirmPurchase(Long purchaseId);

}
