package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Admin;
import com.example.demo.entities.Reservation;
import com.example.demo.entities.Reserver;

public interface ReserverRepository extends JpaRepository<Reserver, Long> {
	Optional<Reserver> findById(Long id);

}
