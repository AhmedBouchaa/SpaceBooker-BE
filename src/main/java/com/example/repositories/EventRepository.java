package com.example.repositories;

import java.util.Optional;

import com.example.entities.Reservation;
import com.example.entities.Reserver;
import com.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	 Optional<Event> findByName(String name);
	Optional<Event> findById(Long id);

	@Modifying
	    @Query("DELETE FROM Event e WHERE e.reserver.id = :id")
	    void deleteByUserId(@Param("id") Long id);

		@Modifying
		@Query("DELETE FROM Event e WHERE e.reservation.id = :id")
		void deleteByReservationId(@Param("id") Reservation reservervation);


}
