package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.Laptop;
import com.freedomofdev.parcinformatique.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://parcinformatiquefodservicess.azurewebsites.net", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/laptops")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @GetMapping
    public List<Laptop> getAllLaptops() {
        return laptopService.getAllLaptops();
    }

    @GetMapping("/names")
    public List<String> getNames() {
        return laptopService.getNames();
    }

    @GetMapping("/rams")
    public List<String> getRams() {
        return laptopService.getRams();
    }

    @GetMapping("/processors")
    public List<String> getProcessors() {
        return laptopService.getProcessors();
    }

    @GetMapping("/display-inches")
    public List<Double> getDisplayInInches() {
        return laptopService.getDisplayInInches();
    }

    @GetMapping("/storages")
    public List<String> getStorages() {
        return laptopService.getStorages();
    }

    @GetMapping("/ratings")
    public List<Double> getRatings() {
        return laptopService.getRatings();
    }

    @GetMapping("/prices-usd")
    public List<Double> getPricesInUsd() {
        return laptopService.getPricesInUsd();
    }
}
