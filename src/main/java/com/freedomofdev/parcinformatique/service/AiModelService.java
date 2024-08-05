package com.freedomofdev.parcinformatique.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedomofdev.parcinformatique.entity.AiModelResponse;
import com.freedomofdev.parcinformatique.entity.InputPayload;
import com.freedomofdev.parcinformatique.entity.ProductRecommendation;
import com.freedomofdev.parcinformatique.entity.Recommendation;
import com.freedomofdev.parcinformatique.repository.InputPayloadRepository;
import com.freedomofdev.parcinformatique.repository.ProductRecommendationRepository;
import com.freedomofdev.parcinformatique.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiModelService {


    @Autowired
    private ProductRecommendationRepository productRecommendationRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private InputPayloadRepository inputPayloadRepository;


    public AiModelResponse getRecommendations(InputPayload payload) {
        String aiModelUrl = generateUrlFromPayload(payload);

        RestTemplate restTemplate = new RestTemplate();

        // Create HttpEntity with InputPayload as body
        HttpEntity<InputPayload> entity = new HttpEntity<>(payload);

        // Call the AI model and get the response
        ResponseEntity<String> response = restTemplate.exchange(aiModelUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                // Parse response to AiModelResponse
                ObjectMapper mapper = new ObjectMapper();
                AiModelResponse aiModelResponse = mapper.readValue(response.getBody(), AiModelResponse.class);

                // Extract the predictedReview value from the AiModelResponse object
                double predictedReview = aiModelResponse.getInputCriteria().getPredictedReview();

                // Set the predictedReview value to the InputPayload object
                payload.setPredictedReview(predictedReview);

                // Create a new Recommendation
                Recommendation recommendation = new Recommendation();

                // Set the Recommendation to the InputPayload
                payload.setRecommendation(recommendation);

                // Set the InputPayload to the Recommendation
                recommendation.setInputCriteria(payload);

                // Save the InputPayload to the database
                payload = inputPayloadRepository.save(payload);

                // Save the Recommendation to the database
                recommendationRepository.save(recommendation);

                // Set the Recommendation for each ProductRecommendation and save them to the database
                for (ProductRecommendation productRecommendation : aiModelResponse.getRecommendedLaptops()) {
                    productRecommendation.setRecommendation(recommendation);
                    productRecommendationRepository.save(productRecommendation);
                }

                return aiModelResponse;
            } catch (JsonProcessingException e) {
                // Handle the exception
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Failed to get response from AI model");
        }

        return null;
    }
    private String generateUrlFromPayload(InputPayload payload) {
        // Implement this method to generate the AI model URL based on the InputPayload
        return "https://upright-vast-stallion.ngrok-free.app/recommend"; // Replace with actual implementation
    }

    public InputPayload saveInputCriteria(InputPayload inputPayload) {
        return inputPayloadRepository.save(inputPayload);
    }
}