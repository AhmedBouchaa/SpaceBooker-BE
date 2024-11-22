package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Reservation;
import com.example.demo.entities.User;
import com.example.demo.repositories.ReservationRepository;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Event;

import com.example.demo.repositories.EventRepository;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	private ReservationRepository reservationRepository;

	@PostMapping
	public Reservation createReservation(@RequestBody Reservation reservation) {
		if (reservation == null) {
			throw new RuntimeException("this reservation can't be null");
		}
		Reservation savedreservation = reservationRepository.save(reservation);

		return savedreservation;
	}

	@GetMapping("in/{date}/from/{start_time}/to/{end_time}")
	public List<Reservation> getReservationsByDateAndTime(@PathVariable LocalDateTime date,
			@PathVariable LocalTime start_time, @PathVariable LocalTime end_time) {
		return reservationRepository.findReservationsByDateAndTime(date, start_time, end_time);
	}

}
