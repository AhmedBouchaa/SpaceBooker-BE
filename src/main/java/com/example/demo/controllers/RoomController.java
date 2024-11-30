package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;
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
	@Autowired
	private AdminRepository adminRepository;
	// create a Room
	@PostMapping
	public Room createRoom(@RequestBody Room room, @RequestParam("bloc_id") int blocId,@RequestParam("admin_id") int adminId) {
		// Vérifier que la Room n'est pas nulle
		if (room == null) {
			throw new RuntimeException("La room ne peut pas être nulle");
		}

		// Vérifier que le numéro de la room n'est pas nul
		if (room.getNum() == 0) {
			throw new RuntimeException("Vous devez attribuer un numéro à la room");
		}

		// Vérifier que le bloc_id n'est pas nul
		if (blocId == 0) {
			throw new RuntimeException("Vous devez spécifier un bloc_id");
		}		// Vérifier que le bloc_id n'est pas nul
		if (adminId == 0) {
			throw new RuntimeException("Vous devez spécifier un admin_id");
		}

		// Vérifier que le numéro n'est pas déjà utilisé
		Optional<Room> existingRoom = roomRepository.findByNum(room.getNum());
		if (existingRoom.isPresent()) {
			throw new RuntimeException("Ce numéro de room existe déjà");
		}
		Bloc bloc =blocRepository.findById(blocId);
		// Sauvegarder la room avec bloc_id
		room.setBloc(bloc);

		Admin admin =adminRepository.findById(adminId);
		// Sauvegarder la room avec bloc_id
		room.setAdmin(admin);

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
