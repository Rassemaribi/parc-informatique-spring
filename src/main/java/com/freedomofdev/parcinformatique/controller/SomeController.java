package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SomeController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_DSI')")
    @GetMapping("/dsi-only")
    public ResponseEntity<String> dsiOnlyEndpoint() {
        return ResponseEntity.ok("This is a DSI only endpoint");
    }

    @PreAuthorize("hasRole('ROLE_COLLAB')")
    @GetMapping("/collab-only")
    public ResponseEntity<String> collabOnlyEndpoint() {
        return ResponseEntity.ok("This is a Collab only endpoint");
    }
}
