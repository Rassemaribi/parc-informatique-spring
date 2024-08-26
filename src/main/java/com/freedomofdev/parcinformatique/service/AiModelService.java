package com.freedomofdev.parcinformatique.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedomofdev.parcinformatique.entity.AiModelResponse;
import com.freedomofdev.parcinformatique.entity.InputPayload;
import com.freedomofdev.parcinformatique.entity.ProductRecommendation;
import com.freedomofdev.parcinformatique.entity.Recommendation;
import com.freedomofdev.parcinformatique.exception.ResourceNotFoundException;
import com.freedomofdev.parcinformatique.repository.InputPayloadRepository;
import com.freedomofdev.parcinformatique.repository.ProductRecommendationRepository;
import com.freedomofdev.parcinformatique.repository.RecommendationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AiModelService {

    private static final Logger logger = LoggerFactory.getLogger(AiModelService.class);

    @Autowired
    private ProductRecommendationRepository productRecommendationRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private InputPayloadRepository inputPayloadRepository;

    @Cacheable(value = "recommendationsCache", key = "#payload.hashCode()")
    public AiModelResponse getRecommendations(InputPayload payload) {
        logger.info("getRecommendations called with payload: {}", payload);

        List<InputPayload> allInputPayloads = inputPayloadRepository.findAll();
        InputPayload lastInputPayload = allInputPayloads.isEmpty() ? null : allInputPayloads.get(allInputPayloads.size() - 1);

        if (lastInputPayload != null && payload.equals(lastInputPayload)) {
            logger.info("InputPayload is the same as the last one, skipping save to database.");
            throw new RuntimeException("Duplicate input - Skipping saving to the database and cache.");
        }

        String aiModelUrl = generateUrlFromPayload(payload);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<InputPayload> entity = new HttpEntity<>(payload);

        long startTime = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.exchange(aiModelUrl, HttpMethod.POST, entity, String.class);
        long endTime = System.currentTimeMillis();
        logger.info("Time taken for getRecommendations: {} ms", (endTime - startTime));

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                AiModelResponse aiModelResponse = mapper.readValue(response.getBody(), AiModelResponse.class);

                double predictedReview = aiModelResponse.getInputCriteria().getPredictedReview();
                payload.setPredictedReview(predictedReview);

                Recommendation recommendation = new Recommendation();
                payload.setRecommendation(recommendation);
                recommendation.setInputCriteria(payload);

                payload = inputPayloadRepository.save(payload);
                recommendationRepository.save(recommendation);

                for (ProductRecommendation productRecommendation : aiModelResponse.getRecommendedLaptops()) {
                    productRecommendation.setRecommendation(recommendation);
                    productRecommendationRepository.save(productRecommendation);
                }

                logger.info("getRecommendations returning data: {}", aiModelResponse);
                return aiModelResponse;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Failed to get response from AI model");
        }

        return null;
    }

    private String generateUrlFromPayload(InputPayload payload) {
        return "https://dockercontainerdeploy-dhgqhef8fsfhbfdv.eastus-01.azurewebsites.net/recommend";
    }

    @Cacheable(value = "inputCriteriaCache", key = "#inputPayload.hashCode()")
    public InputPayload saveInputCriteria(InputPayload inputPayload) {
        logger.info("saveInputCriteria called with inputPayload: {}", inputPayload);

        List<InputPayload> allInputPayloads = inputPayloadRepository.findAll();
        InputPayload lastInputPayload = allInputPayloads.isEmpty() ? null : allInputPayloads.get(allInputPayloads.size() - 1);

        if (lastInputPayload != null && inputPayload.equals(lastInputPayload)) {
            logger.info("InputPayload is the same as the last one, skipping save to database.");
            return lastInputPayload;
        }

        long startTime = System.currentTimeMillis();
        inputPayload = inputPayloadRepository.save(inputPayload);
        long endTime = System.currentTimeMillis();
        logger.info("Time taken for saveInputCriteria: {} ms", (endTime - startTime));

        logger.info("saveInputCriteria returning data: {}", inputPayload);
        return inputPayload;
    }

    public InputPayload getInputCriteria(Long id) {
        return inputPayloadRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("InputPayload", "id", id));
    }
}

