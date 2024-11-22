package com.example.demo.repositories;

import java.util.Optional;

import com.example.demo.entities.Reservation;
import com.example.demo.entities.Reserver;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	Optional<Event> findByName(String name);

	Optional<Event> findById(Long id);

	@Modifying
	@Query("DELETE FROM Event e WHERE e.reserver.id = :id")
	void deleteByUserId(@Param("id") Long id);

	@Modifying
	@Query("DELETE FROM Event e WHERE e.reservation.id = :id")
	void deleteByReservationId(@Param("id") Reservation reservervation);

}
