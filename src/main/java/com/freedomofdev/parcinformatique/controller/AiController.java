package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.InputPayload;
import com.freedomofdev.parcinformatique.service.AiModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin(origins = "https://parcinformatiquefodservicess.azurewebsites.net", maxAge = 3600, allowCredentials = "true") // Adjust the origins to match your Angular app's URL
@RequestMapping("/api")
public class AiController {

    @Autowired
    private AiModelService aiModelService;

    @PostMapping("/recommend")
    public ResponseEntity<String> getRecommendations(@RequestBody InputPayload payload) {
        aiModelService.getRecommendations(payload);
        return ResponseEntity.ok("Recommendations processed");
    }

    @PostMapping("/inputCriteria")
    public ResponseEntity<InputPayload> createInputCriteria(@RequestBody InputPayload inputPayload) {
        InputPayload savedInputPayload = aiModelService.saveInputCriteria(inputPayload);
        return new ResponseEntity<>(savedInputPayload, HttpStatus.CREATED);
    }
}
