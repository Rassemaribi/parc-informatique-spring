package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.service.DemandeAcquisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/demandeAcquisition")
public class DemandeAcquisitionController {
    @Autowired
    private DemandeAcquisitionService demandeAcquisitionService;

    @PreAuthorize("hasRole('COLLABORATEUR')")
    @PostMapping
    public ResponseEntity<DemandeAcquisition> createDemandeAcquisition(@RequestBody DemandeAcquisition demandeAcquisition) {
        DemandeAcquisition createdDemandeAcquisition = demandeAcquisitionService.createDemandeAcquisition(demandeAcquisition);
        return new ResponseEntity<>(createdDemandeAcquisition, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('COLLABORATEUR')")
    @PutMapping("/{id}")
    public ResponseEntity<DemandeAcquisition> updateDemandeAcquisition(@PathVariable Long id, @RequestBody DemandeAcquisition demandeAcquisition) {
        demandeAcquisition.setId(id);
        DemandeAcquisition updatedDemandeAcquisition = demandeAcquisitionService.updateDemandeAcquisition(demandeAcquisition);
        return new ResponseEntity<>(updatedDemandeAcquisition, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DSI')")
    @GetMapping
    public ResponseEntity<List<DemandeAcquisition>> getAllDemandeAcquisitions() {
        List<DemandeAcquisition> demandeAcquisitions = demandeAcquisitionService.getAllDemandeAcquisitions();
        return new ResponseEntity<>(demandeAcquisitions, HttpStatus.OK);
    }
}