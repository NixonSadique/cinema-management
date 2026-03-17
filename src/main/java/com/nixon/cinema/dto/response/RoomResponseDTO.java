package com.nixon.cinema.dto.response;

import com.nixon.cinema.model.enums.RoomType;

import java.util.List;

public record RoomResponseDTO(Long id, String name, RoomType roomType, List<SeatResponseForRoomDTO> seats) {
}
