package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.service.DemandeAcquisitionService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(createdDemandeAcquisition);
    }

    @PreAuthorize("hasRole('DSI')")
    @PutMapping("/accept/{id}")
    public ResponseEntity<DemandeAcquisition> acceptDemandeAcquisition(@PathVariable Long id, @RequestParam Long userId) {
        DemandeAcquisition acceptedDemandeAcquisition = demandeAcquisitionService.acceptDemandeAcquisition(id, userId);
        return ResponseEntity.ok(acceptedDemandeAcquisition);
    }

    @PreAuthorize("hasRole('DSI')")
    @PutMapping("/reject/{id}")
    public ResponseEntity<DemandeAcquisition> rejectDemandeAcquisition(@PathVariable Long id, @RequestParam String rejetMotif, @RequestParam Long userId) {
        DemandeAcquisition rejectedDemandeAcquisition = demandeAcquisitionService.rejectDemandeAcquisition(id, rejetMotif, userId);
        return ResponseEntity.ok(rejectedDemandeAcquisition);
    }

    @PreAuthorize("hasRole('DSI')")
    @PutMapping("/notify/{id}")
    public ResponseEntity<DemandeAcquisition> notifyDemandeAcquisition(@PathVariable Long id) {
        DemandeAcquisition notifiedDemandeAcquisition = demandeAcquisitionService.notifyDemandeAcquisition(id);
        return ResponseEntity.ok(notifiedDemandeAcquisition);
    }

    @PreAuthorize("hasRole('DSI')")
    @GetMapping
    public ResponseEntity<List<DemandeAcquisition>> getAllDemandeAcquisitions() {
        List<DemandeAcquisition> demandeAcquisitions = demandeAcquisitionService.getAllDemandeAcquisitions();
        return ResponseEntity.ok(demandeAcquisitions);
    }

    @PreAuthorize("hasRole('COLLABORATEUR') OR hasRole('DSI')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<DemandeAcquisition>> getDemandesAcquisitionByUserId(@PathVariable Long userId) {
        List<DemandeAcquisition> demandesAcquisition = demandeAcquisitionService.getDemandesAcquisitionByUserId(userId);
        return ResponseEntity.ok(demandesAcquisition);
    }

    @PreAuthorize("hasRole('DSI') OR hasRole('COLLABORATEUR')")
    @GetMapping("/{id}")
    public ResponseEntity<DemandeAcquisition> getDemandeAcquisitionById(@PathVariable Long id) {
        DemandeAcquisition demandeAcquisition = demandeAcquisitionService.getDemandeAcquisitionById(id);
        return ResponseEntity.ok(demandeAcquisition);
    }
}