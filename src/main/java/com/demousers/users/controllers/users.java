package com.demousers.users.controllers;
import com.demousers.users.entidades.UsersModel;
import com.demousers.users.repositories.UserRepository;
import com.demousers.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class users {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UsersModel>> getAllUsers() {
        List<UsersModel> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UsersModel user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<UsersModel> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ RuntimeException.class })
    public String handleRuntimeException(RuntimeException ex) {
        return ex.getMessage();
    }
}
