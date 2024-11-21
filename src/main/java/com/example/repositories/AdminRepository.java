package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

}
