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

	@GetMapping("in/{date}/from/{start_time}/to/{end_time}")
	public List<Reservation> getReservationsByDateAndTime(@PathVariable LocalDateTime date,
			@PathVariable LocalTime start_time, @PathVariable LocalTime end_time) {

		if (end_time.isBefore(start_time)) {
			throw new IllegalArgumentException("The end time must be after the start time.");
		}
		return reservationRepository.findReservationsByDateAndTime(date, start_time, end_time);
	}

}
