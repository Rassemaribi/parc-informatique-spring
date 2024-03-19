package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.service.DemandeAcquisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demande_acquisitions")
public class DemandeAcquisitionController {
    @Autowired
    private DemandeAcquisitionService demandeAcquisitionService;

    @PostMapping
    public ResponseEntity<DemandeAcquisition> createDemandeAcquisition(@RequestBody DemandeAcquisition demandeAcquisition) {
        DemandeAcquisition createdDemandeAcquisition = demandeAcquisitionService.createDemandeAcquisition(demandeAcquisition);
        return new ResponseEntity<>(createdDemandeAcquisition, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemandeAcquisition> updateDemandeAcquisition(@PathVariable Long id, @RequestBody DemandeAcquisition demandeAcquisition) {
        demandeAcquisition.setId(id);
        DemandeAcquisition updatedDemandeAcquisition = demandeAcquisitionService.updateDemandeAcquisition(demandeAcquisition);
        return new ResponseEntity<>(updatedDemandeAcquisition, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DemandeAcquisition>> getAllDemandeAcquisitions() {
        List<DemandeAcquisition> demandeAcquisitions = demandeAcquisitionService.getAllDemandeAcquisitions();
        return new ResponseEntity<>(demandeAcquisitions, HttpStatus.OK);
    }
}