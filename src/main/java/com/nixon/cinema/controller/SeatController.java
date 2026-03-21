package com.nixon.cinema.controller;

import com.nixon.cinema.dto.response.SeatResponseForRoomDTO;
import com.nixon.cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/v1/seat")
public class SeatController {

    private final SeatService service;

    @GetMapping("/{roomId}/")
    ResponseEntity<List<SeatResponseForRoomDTO>> getSeatsByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(service.getAllSeatsByRoomId(roomId));
    }

}
