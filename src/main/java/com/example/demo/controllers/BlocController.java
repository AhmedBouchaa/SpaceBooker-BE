package com.example.demo.controllers;

import com.example.demo.entities.Bloc;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.repositories.BlocRepository;


import java.util.Optional;

@RestController
@RequestMapping("/bloc")
public class BlocController {
    @Autowired
    private BlocRepository blocRepository;
    @GetMapping("/{id}")
    public Bloc getUserById(@PathVariable long id) {
        Optional<Bloc> bloc = blocRepository.findById(id);
        if (bloc.isEmpty()) {
            throw new RuntimeException("didn't find this bloc");
        }
        return bloc.get();
    }
}
