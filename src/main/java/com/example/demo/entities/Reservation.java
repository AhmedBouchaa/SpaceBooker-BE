package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Getter
	@Setter
	private LocalDateTime date;
	@Getter
	@Setter
	private LocalTime start_time;
	@Getter
	@Setter
	private LocalTime end_time;
	@Getter
	@Setter
	private String state;
	@Getter
	@Setter
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "event_id")
	private Event event;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "reserver_id")
	private Reserver reserver;
	@JsonIgnore
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}

	public LocalTime getEnd_time() {
		return end_time;
	}

	public void setEnd_time(LocalTime end_time) {
		this.end_time = end_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Reserver getReserver() {
		return reserver;
	}

	public void setReserver(Reserver reserver) {
		this.reserver = reserver;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
