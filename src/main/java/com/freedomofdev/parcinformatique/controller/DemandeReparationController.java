package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.service.DemandeReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/demandeReparation")
public class DemandeReparationController {
    @Autowired
    private DemandeReparationService demandeReparationService;

    @PreAuthorize("hasRole('COLLABORATEUR') OR hasRole('DSI')")
    @PostMapping
    public ResponseEntity<DemandeReparation> createDemandeReparation(@RequestBody DemandeReparation demandeReparation) {
        DemandeReparation createdDemandeReparation = demandeReparationService.createDemandeReparation(demandeReparation);
        return new ResponseEntity<>(createdDemandeReparation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('DSI')")
    @PutMapping("/accept/{id}")
    public ResponseEntity<DemandeReparation> acceptDemandeReparation(@PathVariable Long id, @RequestParam Long userId) {
        DemandeReparation acceptedDemandeReparation = demandeReparationService.acceptDemandeReparation(id, userId);
        return ResponseEntity.ok(acceptedDemandeReparation);
    }

    @PreAuthorize("hasRole('DSI')")
    @PutMapping("/reject/{id}")
    public ResponseEntity<DemandeReparation> rejectDemandeReparation(@PathVariable Long id, @RequestParam String rejetMotif, @RequestParam Long userId) {
        DemandeReparation rejectedDemandeReparation = demandeReparationService.rejectDemandeReparation(id, rejetMotif, userId);
        return ResponseEntity.ok(rejectedDemandeReparation);
    }

    @PreAuthorize("hasRole('DSI')")
    @PutMapping("/finirSucces/{id}")
    public ResponseEntity<DemandeReparation> finirReparationAvecSucces(@PathVariable Long id) {
        DemandeReparation finishedDemandeReparation = demandeReparationService.finirReparationAvecSucces(id);
        return ResponseEntity.ok(finishedDemandeReparation);
    }

    @PreAuthorize("hasRole('DSI')")
    @PutMapping("/finirEchec/{id}")
    public ResponseEntity<DemandeReparation> finirReparationAvecEchec(@PathVariable Long id, @RequestParam Boolean archive) {
        DemandeReparation finishedDemandeReparation = demandeReparationService.finirReparationAvecEchec(id, archive);
        return ResponseEntity.ok(finishedDemandeReparation);
    }

    @GetMapping
    @PreAuthorize("hasRole('DSI')")
    public ResponseEntity<List<DemandeReparation>> getAllDemandeReparations() {
        List<DemandeReparation> demandeReparations = demandeReparationService.getAllDemandeReparations();
        return new ResponseEntity<>(demandeReparations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('COLLABORATEUR') OR hasRole('DSI')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<DemandeReparation>> getDemandesReparationByUserId(@PathVariable Long userId) {
        List<DemandeReparation> demandesReparation = demandeReparationService.getDemandesReparationByUserId(userId);
        return ResponseEntity.ok(demandesReparation);
    }
}