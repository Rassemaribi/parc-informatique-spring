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

    @PostMapping
    public ResponseEntity<DemandeReparation> createDemandeReparation(@RequestBody DemandeReparation demandeReparation) {
        DemandeReparation createdDemandeReparation = demandeReparationService.createDemandeReparation(demandeReparation);
        return new ResponseEntity<>(createdDemandeReparation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PutMapping("/accept/{id}")
    public ResponseEntity<DemandeReparation> acceptDemandeReparation(@PathVariable Long id, @RequestParam Long userId, @RequestParam Integer estimation) {
        DemandeReparation acceptedDemandeReparation = demandeReparationService.acceptDemandeReparation(id, userId, estimation);
        return ResponseEntity.ok(acceptedDemandeReparation);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PutMapping("/reject/{id}")
    public ResponseEntity<DemandeReparation> rejectDemandeReparation(@PathVariable Long id, @RequestParam String rejetMotif, @RequestParam Long userId) {
        DemandeReparation rejectedDemandeReparation = demandeReparationService.rejectDemandeReparation(id, rejetMotif, userId);
        return ResponseEntity.ok(rejectedDemandeReparation);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PutMapping("/finirSucces/{id}")
    public ResponseEntity<DemandeReparation> finirReparationAvecSucces(@PathVariable Long id) {
        DemandeReparation finishedDemandeReparation = demandeReparationService.finirReparationAvecSucces(id);
        return ResponseEntity.ok(finishedDemandeReparation);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PutMapping("/finirEchec/{id}")
    public ResponseEntity<DemandeReparation> finirReparationAvecEchec(@PathVariable Long id, @RequestParam Boolean archive) {
        DemandeReparation finishedDemandeReparation = demandeReparationService.finirReparationAvecEchec(id, archive);
        return ResponseEntity.ok(finishedDemandeReparation);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @GetMapping
    public ResponseEntity<List<DemandeReparation>> getAllDemandeReparations() {
        List<DemandeReparation> demandeReparations = demandeReparationService.getAllDemandeReparations();
        return new ResponseEntity<>(demandeReparations, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<DemandeReparation>> getDemandesReparationByUserId(@PathVariable Long userId) {
        List<DemandeReparation> demandesReparation = demandeReparationService.getDemandesReparationByUserId(userId);
        return ResponseEntity.ok(demandesReparation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandeReparation> getDemandeReparationById(@PathVariable Long id) {
        DemandeReparation demandeReparation = demandeReparationService.getDemandeReparationById(id);
        return new ResponseEntity<>(demandeReparation, HttpStatus.OK);
    }
}