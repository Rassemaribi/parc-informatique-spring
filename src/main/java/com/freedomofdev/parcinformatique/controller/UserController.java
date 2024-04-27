package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.dto.UserDto;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.security.services.UserDetailsServiceImpl;
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
        return ResponseEntity.ok("Actif assigné avec succès à l'utilisateur");
    }

    @PreAuthorize("hasRole('DSI')")
    @DeleteMapping("/{userId}/removeActif/{actifId}")
    public ResponseEntity<?> removeActifFromUser(@PathVariable Long userId, @PathVariable Long actifId) {
        userService.removeActifFromUser(userId, actifId);
        return ResponseEntity.ok("Actif retiré avec succès de l'utilisateur");
    }

    @PreAuthorize("hasRole('DSI')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DSI')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserAndUnassignActifs(@PathVariable Long userId) {
        userService.deleteUserAndUnassignActifs(userId);
        return ResponseEntity.ok("Utilisateur supprimé et actifs assignés sont désassignés avec succès");
    }
}