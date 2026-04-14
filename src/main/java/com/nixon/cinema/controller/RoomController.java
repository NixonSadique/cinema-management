package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.RoomCreationRequest;
import com.nixon.cinema.dto.response.RoomResponse;
import com.nixon.cinema.dto.response.SimpleRoomResponse;
import com.nixon.cinema.model.enums.RoomType;
import com.nixon.cinema.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/v1")
@Tag(name = "7.Room Controller", description = "Contains the endpoints for the Rooms!")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/rooms")
    @Operation(
            summary = "Create Room",
            description = "Creates a new room, based on the capacity chosen and the number of columns.",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Room Created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
            }
    )
    ResponseEntity<RoomResponse> createRoom(@RequestBody RoomCreationRequest request) {
        return new ResponseEntity<>(roomService.createRoom(request), HttpStatus.CREATED);
    }

    @GetMapping("/rooms")
    @Operation(
            summary = "Retrieves a room",
            description = "Retrieves a room given the name of the room!"
    )
    ResponseEntity<RoomResponse> getRoom(@RequestParam @NotBlank String name) {
        return ResponseEntity.ok(roomService.getRoomByName(name));
    }

    @GetMapping("/rooms/{type}")
    @Operation(
            summary = "Retrieves a room",
            description = "Retrieves a list of rooms given the type of the room!"
    )
    ResponseEntity<List<SimpleRoomResponse>> getRoomByType(@PathVariable RoomType type) {
        return ResponseEntity.ok(roomService.getRoomByType(type));
    }


}
