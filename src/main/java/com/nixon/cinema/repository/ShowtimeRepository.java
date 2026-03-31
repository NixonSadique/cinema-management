package com.nixon.cinema.repository;

import com.nixon.cinema.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findAllByMovieId(Long id);

    @Query("""
            SELECT COUNT(*)
                FROM Showtime s
                WHERE s.room.id = :roomId
                AND s.startTime < :endTime
                AND s.endTime > :startTime
            """)
    Long countOverlappingShowtime(Long roomId, OffsetDateTime startTime, OffsetDateTime endTime);

    Boolean existsByStartTimeAndRoomId(OffsetDateTime start, Long roomId);

    List<Showtime> findAllByStartTime(OffsetDateTime startTime);

    List<Showtime> findAllByRoomId(Long id);

    List<Showtime> findByActiveTrueAndStartTime(OffsetDateTime startTime);

    List<Showtime> findByActiveTrueAndRoomId(Long id);

    List<Showtime> findAllByActiveTrue();
}
