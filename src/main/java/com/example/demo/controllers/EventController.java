package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.*;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.repositories.ReserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repositories.EventRepository;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private ReserverRepository reserverRepository;
	@GetMapping("/{id}")
	public Event getEventById(@PathVariable long id) {
		Optional<Event> event = eventRepository.findById(id);
		if (event.isEmpty()) {
			throw new RuntimeException("didn't find this event");
		}
		return event.get();
	}

	@PostMapping
	public Event createEvent(@RequestBody Event event,@RequestParam("reservation_id") int reservationId,@RequestParam("reserver_id") int reserverId) {
		// Verify that Event is not null
		if (event == null) {
			throw new RuntimeException("the event can not be null");
		}
		// Verify that the Event num is not null
		if (event.getName() == null) {
			throw new RuntimeException("you should at least give a name to the event");
		}
		// Verify that the name doesn't already used
		Optional<Event> existingEvent = eventRepository.findByName(event.getName());
		if (existingEvent.isPresent()) {
			throw new RuntimeException("this name already existe ");
		}
		/*if (reservationId == 0) {
			throw new RuntimeException("Vous devez spécifier un reservationId");
		}

		Reservation reservation =reservationRepository.findById(reservationId);

		event.setReservation(reservation);

		if (reserverId == 0) {
			throw new RuntimeException("Vous devez spécifier un reserverId");
		}

		Reserver reserver =reserverRepository.findById(reserverId);

		event.setReserver(reserver);*/
		Event savedEvent = eventRepository.save(event);
		return savedEvent;
	}
	@GetMapping("/of/{reserver_id}")
	public List<Event> getEventByReserverId(@PathVariable long reserver_id) {
		//Reserver reserver = reserverRepository.findById(reserver_id).orElseThrow(() -> new RuntimeException("Reserver doesn't exist"));
		List<Event> events = eventRepository.getByReserverId(reserver_id);
		if (events.isEmpty()) {
			throw new RuntimeException("there is no events to this reserver");
		}
		return events;
	}

}
