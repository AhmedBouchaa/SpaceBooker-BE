package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.*;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.repositories.ReserverRepository;
import com.example.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repositories.EventRepository;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private ReserverRepository reserverRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private RoomRepository roomRepository;

	@PostMapping
	public Reservation createReservation(@RequestBody Reservation reservation,@RequestParam("reserver_id") int reserverId,@RequestParam("event_id") int eventId,@RequestParam("room_id") int roomId) {
		if (reservation == null) {
			throw new RuntimeException("this reservation can't be null");
		}
		
		System.out.println("Received reservation data:");
		System.out.println("Start time: " + reservation.getStart_time());
		System.out.println("End time: " + reservation.getEnd_time());
		
		if (reservation.getStart_time() == null || reservation.getEnd_time() == null) {
			throw new RuntimeException("start_time and end_time cannot be null");
		}
		if (reserverId == 0) {
			throw new RuntimeException("Vous devez spécifier un reserverId");
		}

		Reserver reserver =reserverRepository.findById(reserverId);

		reservation.setReserver(reserver);

		if (eventId == 0) {
			throw new RuntimeException("Vous devez spécifier un eventId");
		}

		Event event =eventRepository.findById(eventId);
		reservation.setEvent(event);

		if (roomId == 0) {
			throw new RuntimeException("Vous devez spécifier un roomId");
		}

		Room room =roomRepository.findById(roomId);
		reservation.setRoom(room);
		return reservationRepository.save(reservation);
	}

	@GetMapping("from/{start_time}/to/{end_time}")
	public List<Reservation> getReservationsByDateAndTime(
			@PathVariable LocalDateTime start_time, @PathVariable LocalDateTime end_time) {

		if (end_time.isBefore(start_time)) {
			throw new IllegalArgumentException("The end time must be after the start time.");
		}
		return reservationRepository.findReservationsByDateAndTime(start_time, end_time);
	}
	@GetMapping("/check")
	public boolean checkForDuplicateReservation(
			@RequestParam Long roomId,
			@RequestParam String start_time,
			@RequestParam String end_time) {
		LocalDateTime convertedDateStart = LocalDateTime.parse(start_time);
		LocalDateTime convertedDateEnd = LocalDateTime.parse(end_time);
		// Rechercher si une réservation existe déjà avec les mêmes paramètres
		Optional<Reservation> existingReservation = reservationRepository
				.findByRoomAndDate(roomId, convertedDateStart,convertedDateEnd);

		// Si une réservation existe déjà
		if (existingReservation.isPresent()) {
			return false;
		}
		return true;
	}

}
