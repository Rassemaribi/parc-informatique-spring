package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.InputPayload;
import com.freedomofdev.parcinformatique.service.AiModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/recommendAndInputCriteria/{id}")
    public ResponseEntity<Map<String, Object>> getRecommendAndInputCriteria(@PathVariable Long id) {
        InputPayload inputPayload = aiModelService.getInputCriteria(id);
        String recommendations = aiModelService.getRecommendations(inputPayload).toString();

        Map<String, Object> response = new HashMap<>();
        response.put("recommendations", recommendations);
        response.put("inputCriteria", inputPayload);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
