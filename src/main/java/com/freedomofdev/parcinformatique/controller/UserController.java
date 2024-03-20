package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @PreAuthorize("hasRole('DSI') OR hasRole('COLLABORATEUR')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DSI')")
    @PostMapping("/{userId}/assignActif/{actifId}")
    public ResponseEntity<?> assignActifToUser(@PathVariable Long userId, @PathVariable Long actifId) {
        userService.assignActifToUser(userId, actifId);
        return ResponseEntity.ok("Actif assigned successfully to user");
    }

}