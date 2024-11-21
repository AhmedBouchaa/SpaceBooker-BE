package com.example.controllers;


import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entities.Reservation;
import com.example.entities.User;
import com.example.entities.Reserver;
import com.example.repositories.ReservationRepository;
import com.example.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
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
    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new RuntimeException("didn't find this user");
        }
        return users;
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            throw new RuntimeException("didn't find this user");
        }
        return user.get();
    }
    /*@DeleteMapping("/{id}")
    public String deleteuser(@PathVariable long id) {
        Optional<Reserver> user= userRepository.findById(id);
        if(user.isEmpty()){
            throw new RuntimeException("didn't find this user");
        }
        if( ((Reserver)user).getReservations().isEmpty()){
            userRepository.deleteById(id);
            return "user supprimee avec succèes";
        }
        else{
            throw new RuntimeException("Impossible de supprimer le user car il a effectees des reservations");
        }
    }*/
    @PutMapping
    public User updateuser(@RequestBody User upuser)
    {
        User existinguser = userRepository.findById(upuser.getId())
                .orElseThrow(()-> new RuntimeException("didn't find this user"));
        Optional<User> user = userRepository.findByEmail(upuser.getEmail());
        if(user.isPresent() && (!user.get().getEmail().equals(upuser.getEmail()))){
            throw new RuntimeException("a User with this email already exist");
        }
        existinguser.setName(upuser.getName());
        existinguser.setSurname(upuser.getSurname());
        existinguser.setTel(upuser.getTel());
        existinguser.setEmail(upuser.getEmail());
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
    @PostMapping("add/{reservationId}/to/{userId}")
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
    }
    @DeleteMapping("/remove/{reservationId}/to/{userId}")
    public String removeReservationFromuser(@PathVariable long reservationId, @PathVariable long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user non trouve"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new RuntimeException("user non trouve"));

        if(!((Reserver)user).getReservations().contains(reservation)){
            throw new RuntimeException("la reservation n'est pas affecter a cet user");
        }
        ((Reserver)user).getReservations().remove(reservation);
        reservation.setReserver(null);
        userRepository.save(user);
        return "Reservation retire de l'user avec succes";
    }
}
