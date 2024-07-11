package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.dto.UserDto;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{userId}/assignActif/{actifId}")
    public ResponseEntity<?> assignActifToUser(@PathVariable Long userId, @PathVariable Long actifId) {
        userService.assignActifToUser(userId, actifId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/removeActif/{actifId}")
    public ResponseEntity<?> removeActifFromUser(@PathVariable Long userId, @PathVariable Long actifId) {
        userService.removeActifFromUser(userId, actifId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserAndUnassignActifs(@PathVariable Long userId) {
        userService.deleteUserAndUnassignActifs(userId);
        return ResponseEntity.noContent().build();
    }
}