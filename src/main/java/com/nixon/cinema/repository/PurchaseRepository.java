package com.nixon.cinema.repository;

import com.nixon.cinema.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Boolean existsByUserId(Long userId);

    List<Purchase> findAllByUserId(Long userId);
}
