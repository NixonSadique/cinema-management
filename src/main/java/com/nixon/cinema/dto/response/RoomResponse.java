package com.nixon.cinema.dto.response;

import com.nixon.cinema.model.enums.RoomType;

import java.util.List;

public record RoomResponse(
        Long id,
        String name,
        RoomType roomType,
        List<SeatResponseForRoom> seats
) {
}
