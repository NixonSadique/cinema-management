package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.ShowtimeCreationRequestDTO;
import com.nixon.cinema.dto.response.ShowtimeResponseDTO;
import com.nixon.cinema.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cinema/v1/showtime")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService service;

    @PostMapping("/")
    ResponseEntity<String> createShowtimes(@RequestBody ShowtimeCreationRequestDTO request) {
        return new ResponseEntity<>(service.createShowtimesForMovie(request), HttpStatus.CREATED);
    }

    @GetMapping("/")
    ResponseEntity<List<ShowtimeResponseDTO>> getAllShowtimes() {
        return ResponseEntity.ok(service.getAllShowTimes());
    }

    @GetMapping("/active/")
    ResponseEntity<List<ShowtimeResponseDTO>> getAllActiveShowtimes() {
        return ResponseEntity.ok(service.getAllActiveShowTimes());
    }

    @GetMapping("/{id}/movie/")
    ResponseEntity<List<ShowtimeResponseDTO>> getAllShowtimesWithMovieId(@PathVariable Long movieId) {
        return ResponseEntity.ok(service.getAllByMovieId(movieId));
    }

    @GetMapping("/time/")
    ResponseEntity<List<ShowtimeResponseDTO>> getAllShowtimesWithStartTime(@RequestParam LocalDateTime startTime) {
        return ResponseEntity.ok(service.getAllByStartTime(startTime));
    }

    @GetMapping("/{id}/room")
    ResponseEntity<List<ShowtimeResponseDTO>> getAllShowtimesWithRoomId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAllByRoomId(id));
    }

    @GetMapping("/active/time")
    ResponseEntity<List<ShowtimeResponseDTO>> getAllActiveShowtimesWithStartTime(@RequestParam LocalDateTime startTime) {
        return ResponseEntity.ok(service.getByActiveTrueAndStartTime(startTime));
    }

    @GetMapping(value = "/active/{id}/room")
    ResponseEntity<List<ShowtimeResponseDTO>> getAllActiveShowtimesWithRoomId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByActiveTrueAndRoomId(id));
    }
}
