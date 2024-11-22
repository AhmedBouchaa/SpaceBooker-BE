package com.example.repositories;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.Reservation;
import com.example.entities.User;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUser(User user);
    Optional<Reservation> findById(Long id);
    Optional<Reservation> delete(Long id);

    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.reserver.id = :id")
    void deleteByUserId(@Param("id") Long id);


    @Query(
    "from Reservation r where r.date = :date and ((r.startTime <= :start_time and r.endTime >= :start_time) or (r.startTime <= :end_time and r.endTime >= :end_time) or (r.startTime >= :start_time and r.endTime <= :end_time) or (r.startTime <= :start_time and r.endTime >= :end_time))")
    List<Reservation> findReservationsByDateAndTime(@Param("date") LocalDateTime date, @Param("start_time") LocalTime startTime, @Param("end_time") LocalTime endTime
    );
    
}
