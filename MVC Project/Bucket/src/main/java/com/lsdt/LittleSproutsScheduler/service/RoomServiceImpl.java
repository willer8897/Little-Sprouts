package com.lsdt.LittleSproutsScheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lsdt.LittleSproutsScheduler.repository.RoomRepository;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
}
