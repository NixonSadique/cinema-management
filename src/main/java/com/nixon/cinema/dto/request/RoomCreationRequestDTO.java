package com.nixon.cinema.dto.request;

import com.nixon.cinema.model.enums.RoomType;

public record RoomCreationRequestDTO(String name, RoomType type, Integer capacity, Integer columnNumber) {
}
