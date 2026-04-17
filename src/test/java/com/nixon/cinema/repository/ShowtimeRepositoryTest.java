package com.nixon.cinema.repository;

import com.nixon.cinema.model.Room;
import com.nixon.cinema.model.Showtime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ShowtimeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    private Long roomId;

    @Test
    void countOverlappingShowtime_case_start_time_overlaps() {

        var count = showtimeRepository.countOverlappingShowtime(roomId,
                OffsetDateTime.of(2026, 10, 1, 11, 0, 0, 0, UTC),
                OffsetDateTime.of(2026, 10, 1, 13, 0, 0, 0, UTC)
        );

        assertTrue(count != 0);

    }

    @Test
    void countOverlappingShowtime_case_end_time_overlaps() {

        var count = showtimeRepository.countOverlappingShowtime(roomId,
                OffsetDateTime.of(2026, 10, 1, 9, 0, 0, 0, UTC),
                OffsetDateTime.of(2026, 10, 1, 11, 0, 0, 0, UTC)
        );

        assertTrue(count != 0);
    }

    @Test
    void countOverlappingShowtime_case_times_wrap_existing() {

        var count = showtimeRepository.countOverlappingShowtime(roomId,
                OffsetDateTime.of(2026, 10, 1, 9, 0, 0, 0, UTC),
                OffsetDateTime.of(2026, 10, 1, 13, 0, 0, 0, UTC)
        );

        assertTrue(count != 0);
    }

    @Test
    void countOverlappingShowtime_case_times_do_not_overlap() {

        var count = showtimeRepository.countOverlappingShowtime(roomId,
                OffsetDateTime.of(2026, 10, 1, 13, 0, 0, 0, UTC),
                OffsetDateTime.of(2026, 10, 1, 15, 0, 0, 0, UTC)
        );

        assertEquals(0, count);
    }

    @Test
    void countOverlappingShowtime_case_existing_end_time_equals_new_start_time() {

        var count = showtimeRepository.countOverlappingShowtime(roomId,
                OffsetDateTime.of(2026, 10, 1, 12, 0, 0, 0, UTC),
                OffsetDateTime.of(2026, 10, 1, 14, 0, 0, 0, UTC)
        );

        assertEquals(0, count);
    }


    @BeforeEach
    void createExisting() {
        var existingShowtime = new Showtime();

        existingShowtime.setStartTime(OffsetDateTime.of(2026, 10, 1, 10, 0, 0, 0, UTC));
        existingShowtime.setEndTime(OffsetDateTime.of(2026, 10, 1, 12, 0, 0, 0, UTC));

        Room room = new Room();
        room.setName("Room 1");
        entityManager.persist(room);
        roomId = room.getId();

        existingShowtime.setRoom(room);

        entityManager.persist(existingShowtime);
        entityManager.flush();
    }
}