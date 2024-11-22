package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	Optional<Room> findByNum(int num);
}
