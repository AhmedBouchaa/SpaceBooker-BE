package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String description;

	private String img;

	@OneToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	@ManyToOne
	@JoinColumn(name = "reserver_id")
	private Reserver reserver;

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Reserver getReserver() {
		return reserver;
	}

	public void setReserver(Reserver reserver) {
		this.reserver = reserver;
	}

}
