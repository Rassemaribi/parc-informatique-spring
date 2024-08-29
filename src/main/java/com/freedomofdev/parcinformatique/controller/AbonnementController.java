package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.Abonnement;
import com.freedomofdev.parcinformatique.service.AbonnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://parcinformatiquefodservicesss.azurewebsites.net", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/abonnements")
public class AbonnementController {

    private final AbonnementService abonnementService;

    @Autowired
    public AbonnementController(AbonnementService abonnementService) {
        this.abonnementService = abonnementService;
    }

    @GetMapping
    public ResponseEntity<List<Abonnement>> getAllAbonnements() {
        List<Abonnement> abonnements = abonnementService.getAllAbonnements();
        return new ResponseEntity<>(abonnements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Abonnement> getAbonnementById(@PathVariable Long id) {
        Abonnement abonnement = abonnementService.getAbonnementById(id);
        return new ResponseEntity<>(abonnement, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Abonnement> createAbonnement(@RequestBody Abonnement abonnement) {
        Abonnement createdAbonnement = abonnementService.createAbonnement(abonnement);
        return new ResponseEntity<>(createdAbonnement, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Abonnement> updateAbonnement(@PathVariable Long id, @RequestBody Abonnement abonnement) {
        abonnement.setId(id);
        Abonnement updatedAbonnement = abonnementService.updateAbonnement(abonnement);
        return new ResponseEntity<>(updatedAbonnement, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbonnement(@PathVariable Long id) {
        abonnementService.deleteAbonnement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}