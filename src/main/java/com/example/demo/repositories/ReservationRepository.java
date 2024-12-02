package com.example.demo.repositories;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Reserver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Reservation;
import com.example.demo.entities.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> findByReserver(Reserver reserver);

	Optional<Reservation> findById(Long id);

	@Modifying
	@Query("DELETE FROM Reservation r WHERE r.id = :id")
	void delete(@Param("id") Long id);


	@Modifying
	@Query("DELETE FROM Reservation r WHERE r.reserver.id = :id")
	void deleteByUserId(@Param("id") Long id);

	@Query("from Reservation r where NOT (r.end_time <= :start_time OR r.start_time >= :end_time)")
	List<Reservation> findReservationsByDateAndTime(@Param("start_time") LocalDateTime start_time, @Param("end_time") LocalDateTime end_time);

	Reservation findById(int reservationId);

	@Query("from Reservation r where r.room.id = :roomId and NOT (r.end_time <= :start_time OR r.start_time >= :end_time)")
	Optional<Reservation> findByRoomAndDate(@Param("roomId") Long roomId, @Param("start_time") LocalDateTime start_time, @Param("end_time") LocalDateTime end_time);


}
