package com.nixon.cinema.dto.response;

import com.nixon.cinema.model.enums.RoomType;

public record RoomResponseForShowtime(
        Long id,
        String name,
        RoomType roomType,
        Integer capacity
) {
}
