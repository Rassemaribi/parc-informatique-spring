package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisitionArchive;
import com.freedomofdev.parcinformatique.service.DemandeAcquisitionArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "https://parcinformatiquefodservicesss-bgf0bhhae7e4b7am.eastus-01.azurewebsites.net", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/demandeAcquisitionArchive")
public class DemandeAcquisitionArchiveController {

    @Autowired
    private DemandeAcquisitionArchiveService service;

    @GetMapping
    public List<DemandeAcquisitionArchive> getAll() {
        return service.getAll();
    }
}