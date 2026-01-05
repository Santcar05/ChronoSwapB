package com.munwap.backend.controller.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.munwap.backend.dtos.UserDTO;
import com.munwap.backend.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    // localhost:8080/api/users/all (GET)
    @GetMapping("/all")
    public Iterable<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // http://localhost:8080/api/users/{id} (GET)
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // http://localhost:8080/api/users (POST)
    @PostMapping()
    public UserDTO createUser(@RequestBody UserDTO user) {
        userService.saveUser(user);
        return user;
    }
}
