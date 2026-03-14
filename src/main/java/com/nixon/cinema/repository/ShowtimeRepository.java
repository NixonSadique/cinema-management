package com.nixon.cinema.repository;

import com.nixon.cinema.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime,Long> {
    List<Showtime> findAllByMovieId(Long id);

    List<Showtime> findAllByStartTime(LocalDateTime startTime);

    List<Showtime> findAllByRoomId(Long id);

    List<Showtime> findByActiveTrueAndStartTime(LocalDateTime startTime);

    List<Showtime> findByActiveTrueAndRoomId(Long id);

    List<Showtime> findAllByActiveTrue();
}
