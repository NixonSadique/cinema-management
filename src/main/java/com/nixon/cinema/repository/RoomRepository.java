package com.nixon.cinema.repository;

import com.nixon.cinema.model.Room;
import com.nixon.cinema.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);

    List<Room> findAllByRoomType(RoomType roomType);

}
