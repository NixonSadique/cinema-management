package com.nixon.cinema.dto.response;

import com.nixon.cinema.model.enums.RoomType;

public record SimpleRoomResponse(
        Long id,
        String name,
        RoomType roomType,
        Integer capacity
) {
}
