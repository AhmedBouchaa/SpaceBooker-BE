package com.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Admin;
import com.example.entities.Reservation;
import com.example.entities.Reserver;

public interface ReserverRepository extends JpaRepository<Reserver, Long>{
    Optional<Reserver> findById(Long id);

}
