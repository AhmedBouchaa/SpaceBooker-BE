package com.example.demo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Admin extends User {

    @JsonIgnore
	@OneToMany(mappedBy = "admin")
	private List<Room> rooms;
}
