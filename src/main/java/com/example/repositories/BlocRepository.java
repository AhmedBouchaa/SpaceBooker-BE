package com.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Bloc;

public interface BlocRepository extends JpaRepository<Bloc, Long>{
	 Optional<Bloc> findById(int id);

}
