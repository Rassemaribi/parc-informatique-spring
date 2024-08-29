package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.InputPayload;
import com.freedomofdev.parcinformatique.service.AiModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://parcinformatiquefodservicesss-bgf0bhhae7e4b7am.eastus-01.azurewebsites.net", maxAge = 3600, allowCredentials = "true")// Adjust the origins to match your Angular app's URL
@RequestMapping("/api")
public class AiController {

    @Autowired
    private AiModelService aiModelService;

    @PostMapping("/recommend")
    public ResponseEntity<Object> getRecommendations(@RequestBody InputPayload payload) {
        Object recommendations = aiModelService.getRecommendations(payload);
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/inputCriteria")
    public ResponseEntity<InputPayload> createInputCriteria(@RequestBody InputPayload inputPayload) {
        InputPayload savedInputPayload = aiModelService.saveInputCriteria(inputPayload);
        return new ResponseEntity<>(savedInputPayload, HttpStatus.CREATED);
    }
}
