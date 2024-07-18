package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.dto.AuthRequest;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://parcinformatiquefodservicess.azurewebsites.net", maxAge = 3600, allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/auth")
    public ResponseEntity<User> authenticate(@RequestBody AuthRequest authRequest) {
        User user = userService.findOrCreateUser(authRequest);
        return ResponseEntity.ok(user);
    }
}
