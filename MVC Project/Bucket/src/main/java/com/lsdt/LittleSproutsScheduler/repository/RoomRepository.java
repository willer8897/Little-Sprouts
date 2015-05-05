package com.lsdt.LittleSproutsScheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lsdt.LittleSproutsScheduler.model.Room;

@Repository("roomRepository")
public interface RoomRepository  extends JpaRepository<Room, Long>{

}
