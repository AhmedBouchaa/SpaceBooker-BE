package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Reserver extends User {
	@OneToMany(mappedBy = "reserver")
	private List<Reservation> reservations;

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public void addReservation(Reservation reservation) {
		this.reservations.add(reservation);
	}
}
