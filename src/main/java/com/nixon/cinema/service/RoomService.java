package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.RoomCreationRequest;
import com.nixon.cinema.dto.response.RoomResponse;
import com.nixon.cinema.dto.response.SimpleRoomResponse;
import com.nixon.cinema.model.enums.RoomType;

import java.util.List;

public interface RoomService {

    RoomResponse createRoom(RoomCreationRequest request);

    RoomResponse getRoomByName(String name);

    List<SimpleRoomResponse> getRoomByType(RoomType type);
}
