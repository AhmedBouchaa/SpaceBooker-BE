package com.example.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Event;

import com.example.repositories.EventRepository;

@RestController
@RequestMapping("/events")
public class EventController {
	
	private EventRepository eventRepository;
	@PostMapping
	public Event createEvent(@RequestBody Event event) {
		//Verify that Event is not null
		if(event == null) {
			throw new RuntimeException("the event can not be null");
		}
		//Verify that the Event num is not null
		if(event.getName() == null) {
			throw new RuntimeException("you should at least give a name to the event");
		}
		//Verify that the name doesn't already used
        Optional<Event> existingEvent =eventRepository.findByName(event.getName());
        if(existingEvent.isPresent()){
          throw new RuntimeException("this name already existe ");
        }
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
	}

}
