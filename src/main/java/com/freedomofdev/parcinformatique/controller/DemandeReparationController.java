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

    @PreAuthorize("hasRole('COLLABORATEUR') OR hasRole('DSI')")
    @PutMapping("/{id}")
    public ResponseEntity<DemandeReparation> updateDemandeReparation(@PathVariable Long id, @RequestBody DemandeReparation demandeReparation) {
        demandeReparation.setId(id);
        DemandeReparation updatedDemandeReparation = demandeReparationService.updateDemandeReparation(demandeReparation);
        return new ResponseEntity<>(updatedDemandeReparation, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('DSI')")
    public ResponseEntity<List<DemandeReparation>> getAllDemandeReparations() {
        List<DemandeReparation> demandeReparations = demandeReparationService.getAllDemandeReparations();
        return new ResponseEntity<>(demandeReparations, HttpStatus.OK);
    }
}
