package com.nixon.cinema.controller;

import com.nixon.cinema.dto.response.SeatResponseForRoom;
import com.nixon.cinema.service.SeatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/v1")
@Tag(name = "8.Seat Controller", description = "Contains the endpoints for the Seats!")
public class SeatController {

    private final SeatService service;

    @GetMapping("/seats/{roomId}")
    ResponseEntity<List<SeatResponseForRoom>> getSeatsByRoom(@RequestParam Long roomId) {
        return ResponseEntity.ok(service.getAllSeatsByRoomId(roomId));
    }

}
