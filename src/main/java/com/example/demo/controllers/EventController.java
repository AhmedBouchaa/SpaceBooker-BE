package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Event;

import com.example.demo.repositories.EventRepository;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventRepository eventRepository;

	@GetMapping("/{id}")
	public Event getEventById(@PathVariable long id) {
		Optional<Event> event = eventRepository.findById(id);
		if (event.isEmpty()) {
			throw new RuntimeException("didn't find this event");
		}
		return event.get();
	}

	@PostMapping
	public Event createEvent(@RequestBody Event event) {
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
		Event savedEvent = eventRepository.save(event);
		return savedEvent;
	}

}
