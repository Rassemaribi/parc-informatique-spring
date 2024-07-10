package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.dto.AuthRequest;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/auth")
    public ResponseEntity<User> authenticate(@RequestBody AuthRequest authRequest) {
        User user = userService.findOrCreateUser(authRequest);
        return ResponseEntity.ok(user);
    }
}
