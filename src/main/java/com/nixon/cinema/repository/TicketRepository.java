package com.nixon.cinema.repository;

import com.nixon.cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Boolean existsByShowtimeId(Long id);

    List<Ticket> findByShowtimeId(Long id);

    List<Ticket> findByPurchaseId(Long id);

    List<Ticket> findBySeatIdAndShowtimeId(Long seatId, Long showtimeId);

    Boolean existsBySeatIdAndShowtimeId(Long seatId, Long showtimeId);
}
