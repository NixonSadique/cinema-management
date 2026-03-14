package com.nixon.cinema.repository;

import com.nixon.cinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByRoomId(Long id);

}
