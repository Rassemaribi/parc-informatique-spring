package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.service.ActifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/actifs")
public class ActifController {

    private final ActifService actifService;

    @Autowired
    public ActifController(ActifService actifService) {
        this.actifService = actifService;
    }

    @PreAuthorize("hasRole('DSI')")
    @PostMapping("/batch")
    public ResponseEntity<List<Actif>> createActifs(@RequestBody List<Actif> actifs, @RequestParam Long id) {
        List<Actif> createdActifs = actifService.createActifs(actifs, id);
        return new ResponseEntity<>(createdActifs, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('DSI')")
    @GetMapping
    public ResponseEntity<List<Actif>> getAllActifs() {
        List<Actif> actifs = actifService.getAllActifs();
        return new ResponseEntity<>(actifs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DSI')")
    @GetMapping("/{id}")
    public ResponseEntity<Actif> getActifById(@PathVariable Long id) {
        Actif actif = actifService.getActifById(id);
        if (actif != null) {
            return new ResponseEntity<>(actif, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('DSI')")
    @PutMapping("/{id}")
    public ResponseEntity<Actif> updateActif(@PathVariable Long id, @RequestBody Actif actif) {
        actif.setId(id);
        actif.setAssignedUser(null); // Do not set the user property from the request body
        Actif updatedActif = actifService.updateActif(actif);
        if (updatedActif != null) {
            return new ResponseEntity<>(updatedActif, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('DSI')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActif(@PathVariable Long id) {
        actifService.deleteActif(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}