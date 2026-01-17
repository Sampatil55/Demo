package com.hotel.controller;

import com.hotel.service.OpenAIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    // GET example: fixed prompt
    @GetMapping("/story")
    public String story() {
        return openAIService.generateResponse(
                "Tell me a three sentence bedtime story about a unicorn."
        );
    }

    // POST example: dynamic prompt from request body
    @PostMapping("/ask")
    public String ask(@RequestBody String prompt) {
        return openAIService.generateResponse(prompt);
    }
}

