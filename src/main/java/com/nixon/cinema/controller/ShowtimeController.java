package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.ShowtimeCreationRequest;
import com.nixon.cinema.dto.response.ShowtimeResponse;
import com.nixon.cinema.service.ShowtimeService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Create A showtime",
            description = "Endpoint to create a showtime. <br>" +
                    "P.S.: A ROOM and a MOVIE must already be created!"
    )
    @PostMapping("/showtimes")
    ResponseEntity<String> createShowtimes(@RequestBody ShowtimeCreationRequest request) {
        return new ResponseEntity<>(service.createShowtimesForMovie(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All",
            description = "Get all showtimes, active and inactive!"
    )
    @GetMapping("/showtimes")
    ResponseEntity<List<ShowtimeResponse>> getAllShowtimes() {
        return ResponseEntity.ok(service.getAllShowTimes());
    }

    @Operation(
            summary = "Get by movie",
            description = "Get all showtimes for a movie!"
    )
    @GetMapping("/showtimes{movieId}")
    ResponseEntity<List<ShowtimeResponse>> getAllShowtimesWithMovieId(@RequestParam Long movieId) {
        return ResponseEntity.ok(service.getAllByMovieId(movieId));
    }

    @Operation(
            summary = "Get by startTime",
            description = "Get all showtimes by starting time"
    )
    @GetMapping("/showtimes/start/{startTime}")
    ResponseEntity<List<ShowtimeResponse>> getAllShowtimesWithStartTime(@PathVariable OffsetDateTime startTime) {
        return ResponseEntity.ok(service.getAllByStartTime(startTime));
    }

    @Operation(
            summary = "Get by room",
            description = "Get all showtimes, active and inactive, by room!"
    )
    @GetMapping("/showtimes/room/{roomId}")
    ResponseEntity<List<ShowtimeResponse>> getAllShowtimesWithRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(service.getAllByRoomId(roomId));
    }

    @Operation(
            summary = "Get All active",
            description = "Get all showtimes active!"
    )
    @GetMapping("/showtimes/active")
    ResponseEntity<List<ShowtimeResponse>> getAllActiveShowtimes() {
        return ResponseEntity.ok(service.getAllActiveShowTimes());
    }

    @Operation(
            summary = "Get active by room",
            description = "Get all active showtimes by room!"
    )
    @GetMapping(value = "/showtimes/active/room/{roomId}")
    ResponseEntity<List<ShowtimeResponse>> getAllActiveShowtimesWithRoomId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByActiveTrueAndRoomId(id));
    }

    @Operation(
            summary = "Get active by start time",
            description = "Get all active showtimes by start time!"
    )
    @GetMapping("/showtimes/active{startTime}")
    ResponseEntity<List<ShowtimeResponse>> getAllActiveShowtimesWithStartTime(@RequestParam OffsetDateTime startTime) {
        return ResponseEntity.ok(service.getByActiveTrueAndStartTime(startTime));
    }


}
