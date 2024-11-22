package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findById(Long id);

	@Query("from User u where upper(u.name) like upper(concat('%',:keyword,'%') ) or upper(u.surname) like upper(concat('%',:keyword,'%') )")
	List<User> findByNomContainingIgnoreCase(String keyword);
}