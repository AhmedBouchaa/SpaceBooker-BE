package com.example.controllers;


import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entities.Reservation;
import com.example.entities.User;
import com.example.entities.Reserver;
import com.example.repositories.ReservationRepository;
import com.example.repositories.ReserverRepository;
import com.example.repositories.UserRepository;
import com.example.repositories.EventRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReserverRepository reserverRepository;
    @Autowired
    private EventRepository eventRepository;
    @PostMapping
    public User createUser(@RequestBody User user) {
        if(user == null){
            throw new RuntimeException("this user can't be null");
        }
        if(user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getPwd() == null){
            throw new RuntimeException("the email and password are null");
        }
        Optional<User> existinguser =userRepository.findByEmail(user.getEmail());
        if(existinguser.isPresent()){
          throw new RuntimeException("this email already exite");
        }
        User saveduser = userRepository.save(user);

        return saveduser;
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            throw new RuntimeException("didn't find this user");
        }
        return user.get();
    }
    @DeleteMapping("/{id}")
    public void deleteuser(@PathVariable long id) {
        Optional<Reserver> reserver= reserverRepository.findById(id);
        if(reserver.isEmpty()){
            throw new RuntimeException("didn't find this user");
        }
        userRepository.deleteById(id);
        reservationRepository.deleteByUserId(id);
        eventRepository.deleteByUserId(id);
    }
    
    @PutMapping
    public User updateuser(@RequestBody User upuser)
    {
        User existinguser = userRepository.findById(upuser.getId())
                .orElseThrow(()-> new RuntimeException("didn't find this user"));
        Optional<User> user = userRepository.findByEmail(upuser.getEmail());
        existinguser.setName(upuser.getName());
        existinguser.setSurname(upuser.getSurname());
        existinguser.setTel(upuser.getTel());
        existinguser.setPwd(upuser.getPwd());
        return userRepository.save(existinguser);
    }
    @GetMapping("/{id}/reservations")
    public List<Reservation> getReservationsByuserId(@PathVariable long id) {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("user non trouve"));
        List<Reservation> reservations = ((Reserver)user).getReservations();
        if(reservations.isEmpty()){
            throw new RuntimeException("Aucune reservation assossie a cet user");
        }
        return reservations;
    }
    @GetMapping("/recherche")
    public List<User> searchuser(@RequestParam String keyword) {
        List<User> resultats=userRepository.findByNomContainingIgnoreCase(keyword);
        if(resultats.isEmpty()){
            throw new RuntimeException("Aucun user trouvé avec le mot-clé:" + keyword);
        }
        return resultats;
    }
  /*  @PostMapping("add/{reservationId}/to/{userId}")
    public String addReservationTouser(@PathVariable long reservationId, @PathVariable long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user non trouve"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new RuntimeException("user non trouve"));
        if(reservation.getReserver() != null && reservation.getReserver().getId() == userId){
            throw new RuntimeException("la reservation est deja affecter a cet user");
        }
        reservation.setReserver((Reserver)user);
        ((Reserver)user).addReservation(reservation);
        userRepository.save(user);
        return "Reservation affecte";
    }*/

    @DeleteMapping("/remove/{reservationId}/from/{userId}")
    public String removeReservationFromuser(@PathVariable long reservationId, @PathVariable long userId) {
        Reserver reserver = reserverRepository.findById(userId).orElseThrow(()->new RuntimeException("user non trouve"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new RuntimeException("Reservation non trouve"));

        if(!(reserver.getReservations().contains(reservation))){
            throw new RuntimeException("la reservation n'est pas affecter a cet user");
        }
        reserver.getReservations().remove(reservation);
        reservationRepository.delete(reservation);
        eventRepository.deleteByReservationId(reservation);
        userRepository.save(reserver);
        return "Reservation retire de l'user avec succes";
    }
}
