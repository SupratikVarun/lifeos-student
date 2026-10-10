package com.lifeos.student.ai.controller;

import com.lifeos.student.ai.dto.AIRequestDTO;
import com.lifeos.student.ai.dto.AIResponseDTO;
import com.lifeos.student.ai.service.AIManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIManagerService aiManagerService;

    @PostMapping("/summarize")
    public AIResponseDTO summarize(@RequestBody AIRequestDTO request) {

        return aiManagerService.summarize(request);

    }

    @PostMapping("/explain")
    public AIResponseDTO explain(@RequestBody AIRequestDTO request) {

        System.out.println("Controller called");
        System.out.println(request);

        AIResponseDTO response = aiManagerService.explain(request);

        System.out.println(response);

        return response;
    }
    @PostMapping("/flashcards")
    public AIResponseDTO flashcards(@RequestBody AIRequestDTO request) {

        return aiManagerService.generateFlashcards(request);

    }

    @PostMapping("/quiz")
    public AIResponseDTO quiz(@RequestBody AIRequestDTO request) {

        return aiManagerService.generateQuiz(request);

    }

    @PostMapping("/ask")
    public AIResponseDTO askQuestion(@RequestBody AIRequestDTO request) {

        return aiManagerService.askQuestion(request);

    }

}