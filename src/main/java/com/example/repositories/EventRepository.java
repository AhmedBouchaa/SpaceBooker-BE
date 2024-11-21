package com.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	 Optional<Event> findByName(String name);

}
