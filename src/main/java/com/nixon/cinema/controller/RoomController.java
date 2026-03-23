package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.RoomCreationRequestDTO;
import com.nixon.cinema.dto.response.RoomResponseDTO;
import com.nixon.cinema.model.enums.RoomType;
import com.nixon.cinema.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/v1/room")
@Tag(name = "7.Room Controller", description = "Contains the endpoints for the Rooms!")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create Room",
            description = "Creates a new room, based on the capacity chosen and the number of columns.",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Room Created",
                            content = @Content(schema = @Schema(implementation = RoomResponseDTO.class))
                    )
            }
    )
    ResponseEntity<RoomResponseDTO> createRoom(@RequestBody RoomCreationRequestDTO request) {
        return new ResponseEntity<>(roomService.createRoom(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieves a room",
            description = "Retrieves a room given the name of the room!",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Room Not Found",
                            content = @Content(schema = @Schema(implementation = RoomResponseDTO.class))
                    )
            }
    )
    @GetMapping(value = "/{name}")
    ResponseEntity<RoomResponseDTO> getRoom(@PathVariable String name) {
        return ResponseEntity.ok(roomService.getRoomByName(name));
    }

    @Operation(
            summary = "Retrieves a room",
            description = "Retrieves a room given the name of the room!",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Room Not Found",
                            content = @Content(schema = @Schema(implementation = RoomResponseDTO.class))
                    )
            }
    )
    @GetMapping("/room/{type}")
    ResponseEntity<List<RoomResponseDTO>> getRoomByType(@PathVariable RoomType type) {
        return ResponseEntity.ok(roomService.getRoomByType(type));
    }


}
