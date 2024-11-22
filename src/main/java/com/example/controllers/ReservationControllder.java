package com.example.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.entities.Reservation;
import com.example.entities.User;
import com.example.repositories.ReservationRepository;
import org.springframework.web.bind.annotation.*;

import com.example.entities.Event;

import com.example.repositories.EventRepository;

@RestController
@RequestMapping("/reservation")
public class ReservationControllder {

    private ReservationRepository reservationRepository;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        if(reservation == null){
            throw new RuntimeException("this reservation can't be null");
        }
        Reservation savedreservation = reservationRepository.save(reservation);

        return savedreservation;
    }

    @GetMapping("in/{date}/from/{start_time}/to/{end_time}")
    public List<Reservation> getReservationsByDateAndTime(@PathVariable LocalDateTime date, @PathVariable LocalTime start_time, @PathVariable LocalTime end_time) {
        return reservationRepository.findReservationsByDateAndTime(date,start_time,end_time);
    }

}
