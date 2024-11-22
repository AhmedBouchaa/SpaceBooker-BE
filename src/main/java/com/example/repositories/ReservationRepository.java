package com.example.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.Reservation;
import com.example.entities.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUser(User user);
    Optional<Reservation> findById(Long id);
    
    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.reserver.id = :id")
    void deleteByUserId(@Param("id") Long id);
    
}
