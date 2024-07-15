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
@CrossOrigin(origins = "https://parcinformatiquefodservices.azurewebsites.net", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/actifs")
public class ActifController {

    private final ActifService actifService;

    @Autowired
    public ActifController(ActifService actifService) {
        this.actifService = actifService;
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PostMapping("/batch")
    public ResponseEntity<List<Actif>> createActifs(@RequestBody List<Actif> actifs, @RequestParam Long id) {
        List<Actif> createdActifs = actifService.createActifs(actifs, id);
        return new ResponseEntity<>(createdActifs, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @GetMapping
    public ResponseEntity<List<Actif>> getAllActifs() {
        List<Actif> actifs = actifService.getAllActifs();
        return new ResponseEntity<>(actifs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actif> getActifById(@PathVariable Long id) {
        Actif actif = actifService.getActifById(id);
        return new ResponseEntity<>(actif, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PutMapping("/{id}")
    public ResponseEntity<Actif> updateActif(@PathVariable Long id, @RequestBody Actif actif) {
        actif.setId(id);
        actif.setAssignedUser(null); // Do not set the user property from the request body
        Actif updatedActif = actifService.updateActif(actif);
        return new ResponseEntity<>(updatedActif, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PutMapping("/archive/{id}")
    public ResponseEntity<Actif> archiveActif(@PathVariable Long id) {
        Actif archivedActif = actifService.archiveActif(id);
        return new ResponseEntity<>(archivedActif, HttpStatus.OK);
    }
}