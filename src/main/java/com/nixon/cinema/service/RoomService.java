package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.RoomCreationRequestDTO;
import com.nixon.cinema.dto.response.RoomResponseDTO;
import com.nixon.cinema.model.enums.RoomType;

import java.util.List;

public interface RoomService {

    RoomResponseDTO createRoom(RoomCreationRequestDTO request);

    RoomResponseDTO getRoomByName(String name);

    List<RoomResponseDTO> getRoomByType(RoomType type);
}
