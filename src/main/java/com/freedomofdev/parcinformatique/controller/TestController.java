package com.freedomofdev.parcinformatique.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/collaborateur")
    @PreAuthorize("hasRole('collaborateur') or hasRole('DSI')")
    public String collaborateurAccess() {
        return "Collaborateur Content.";
    }

    @GetMapping("/DSI")
    @PreAuthorize("hasRole('DSI')")
    public String DSIAccess() {
        return "DSI Board.";
    }
}