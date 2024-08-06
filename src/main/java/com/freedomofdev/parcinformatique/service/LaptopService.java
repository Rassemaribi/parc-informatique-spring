package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Laptop;
import com.freedomofdev.parcinformatique.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    public List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }

    public List<String> getNames() {
        return laptopRepository.findAll().stream()
                .map(Laptop::getName)
                .collect(Collectors.toList());
    }

    public List<String> getRams() {
        return laptopRepository.findAll().stream()
                .map(Laptop::getRam)
                .collect(Collectors.toList());
    }

    public List<String> getProcessors() {
        return laptopRepository.findAll().stream()
                .map(Laptop::getProcessor)
                .collect(Collectors.toList());
    }

    public List<Double> getDisplayInInches() {
        return laptopRepository.findAll().stream()
                .map(Laptop::getDisplayInInch)
                .collect(Collectors.toList());
    }

    public List<String> getStorages() {
        return laptopRepository.findAll().stream()
                .map(Laptop::getStorage)
                .collect(Collectors.toList());
    }

    public List<Double> getRatings() {
        return laptopRepository.findAll().stream()
                .map(Laptop::getRating)
                .collect(Collectors.toList());
    }

    public List<Double> getPricesInUsd() {
        return laptopRepository.findAll().stream()
                .map(Laptop::getPriceInUsd)
                .collect(Collectors.toList());
    }
}
