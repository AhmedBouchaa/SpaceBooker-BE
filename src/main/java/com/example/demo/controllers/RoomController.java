package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Bloc;
import com.example.demo.entities.Room;

import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.RoomRepository;

@RestController
@RequestMapping("rooms")
public class RoomController {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private BlocRepository blocRepository;

	// create a Room
	@PostMapping
	public Room createRoom(@RequestBody Room room) {
		// Verify that Room is not null
		if (room == null) {
			throw new RuntimeException("the room can not be null");
		}
		// Verify that the room num is not null
		if (room.getNum() == 0) {
			throw new RuntimeException("you should at least give a number to a room");
		}
		// Verify that the num doesn't already used
		Optional<Room> existingRoom = roomRepository.findByNum(room.getNum());
		if (existingRoom.isPresent()) {
			throw new RuntimeException("this num already existe ");
		}
		return roomRepository.save(room);
	}

	@GetMapping
	public List<Room> getRooms() {
		List<Room> rooms = roomRepository.findAll();
		if (rooms.isEmpty()) {
			throw new RuntimeException("there is no room");
		}
		return rooms;
	}
	@GetMapping("/bloc/{bloc_id}")
	public List<Room> getRoomsByBlocId(@PathVariable long bloc_id) {
		Bloc bloc = blocRepository.findById(bloc_id).orElseThrow(() -> new RuntimeException("Bloc doesn't exist"));
		List<Room> rooms = bloc.getRooms();
		if (rooms.isEmpty()) {
			throw new RuntimeException("there is no room in this bloc");
		}
		return rooms;
	}
}
