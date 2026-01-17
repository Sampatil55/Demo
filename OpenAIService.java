package com.hotel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;


    private final String OPENAI_URL = "https://api.openai.com/v1/responses";

    public String generateResponse(String prompt) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("OPENAI_API_KEY is not set");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // JSON body as a simple String
        String body = "{"
                + "\"model\":\"gpt-4.1\","
                + "\"input\":\"" + prompt + "\","
                + "\"temperature\":0.7,"
                + "\"max_output_tokens\":150"
                + "}";

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_URL, request, String.class);

        return response.getBody();
    }
}
