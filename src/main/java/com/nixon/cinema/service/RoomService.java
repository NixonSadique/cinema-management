package com.nixon.cinema.service;

import com.nixon.cinema.model.enums.RoomType;

public interface RoomService {
    RoomResponseDTO getRoomByName(String name);

    RoomResponseDTO getRoomByType(RoomType type);
}
