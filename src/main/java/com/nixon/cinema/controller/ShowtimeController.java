package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.ShowtimeCreationRequest;
import com.nixon.cinema.dto.response.ShowtimeResponse;
import com.nixon.cinema.service.ShowtimeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/cinema/v1")
@RequiredArgsConstructor
@Tag(name = "5.Showtime Controller", description = "Contains the endpoints for the Showtimes")
public class ShowtimeController {

    private final ShowtimeService service;

    @PostMapping("/showtimes")
    ResponseEntity<String> createShowtimes(@RequestBody ShowtimeCreationRequest request) {
        return new ResponseEntity<>(service.createShowtimesForMovie(request), HttpStatus.CREATED);
    }

    @GetMapping("/showtimes")
    ResponseEntity<List<ShowtimeResponse>> getAllShowtimes() {
        return ResponseEntity.ok(service.getAllShowTimes());
    }

    @GetMapping("/showtimes{movieId}")
    ResponseEntity<List<ShowtimeResponse>> getAllShowtimesWithMovieId(@RequestParam Long movieId) {
        return ResponseEntity.ok(service.getAllByMovieId(movieId));
    }

    @GetMapping("/showtimes/start/{startTime}")
    ResponseEntity<List<ShowtimeResponse>> getAllShowtimesWithStartTime(@PathVariable OffsetDateTime startTime) {
        return ResponseEntity.ok(service.getAllByStartTime(startTime));
    }

    @GetMapping("/showtimes/room/{roomId}")
    ResponseEntity<List<ShowtimeResponse>> getAllShowtimesWithRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(service.getAllByRoomId(roomId));
    }

    @GetMapping("/showtimes/active")
    ResponseEntity<List<ShowtimeResponse>> getAllActiveShowtimes() {
        return ResponseEntity.ok(service.getAllActiveShowTimes());
    }

    @GetMapping(value = "/showtimes/active/room/{roomId}")
    ResponseEntity<List<ShowtimeResponse>> getAllActiveShowtimesWithRoomId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByActiveTrueAndRoomId(id));
    }

    @GetMapping("/showtimes/active{startTime}")
    ResponseEntity<List<ShowtimeResponse>> getAllActiveShowtimesWithStartTime(@RequestParam OffsetDateTime startTime) {
        return ResponseEntity.ok(service.getByActiveTrueAndStartTime(startTime));
    }


}
