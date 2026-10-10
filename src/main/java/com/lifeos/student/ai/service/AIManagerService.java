package com.lifeos.student.ai.service;

import com.lifeos.student.ai.dto.AIRequestDTO;
import com.lifeos.student.ai.dto.AIResponseDTO;
import com.lifeos.student.ai.enums.ProviderType;
import com.lifeos.student.ai.provider.gemini.GeminiProvider;
import com.lifeos.student.ai.provider.groq.GroqProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIManagerService {

    private final GeminiProvider geminiProvider;
    private final GroqProvider groqProvider;

    public AIResponseDTO summarize(AIRequestDTO request) {

        long start = System.currentTimeMillis();

        try {

            String response;

            if (request.getProvider() == ProviderType.GEMINI) {

                response = geminiProvider.summarize(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            if (request.getProvider() == ProviderType.GROQ) {

                response = groqProvider.summarize(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            // AUTO MODE
            try {

                response = geminiProvider.summarize(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();

            } catch (Exception ex) {

                response = groqProvider.summarize(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(true)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

        } catch (Exception ex) {

            throw new RuntimeException("Both AI providers are unavailable.");
        }
    }

    public AIResponseDTO explain(AIRequestDTO request) {

        long start = System.currentTimeMillis();

        try {

            String response;

            // GEMINI
            if (request.getProvider() == ProviderType.GEMINI) {

                response = geminiProvider.explain(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            // GROQ
            if (request.getProvider() == ProviderType.GROQ) {

                response = groqProvider.explain(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            // AUTO MODE
            try {

                response = geminiProvider.explain(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();

            } catch (Exception ex) {

                response = groqProvider.explain(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(true)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

        } catch (Exception ex) {

            throw new RuntimeException("Both AI providers are unavailable.");
        }
    }

    public AIResponseDTO generateFlashcards(AIRequestDTO request) {

        long start = System.currentTimeMillis();

        try {

            String response;

            if (request.getProvider() == ProviderType.GEMINI) {

                response = geminiProvider.generateFlashcards(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            if (request.getProvider() == ProviderType.GROQ) {

                response = groqProvider.generateFlashcards(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            // AUTO MODE
            try {

                response = geminiProvider.generateFlashcards(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();

            } catch (Exception ex) {

                response = groqProvider.generateFlashcards(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(true)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

        } catch (Exception ex) {

            throw new RuntimeException("Both AI providers are unavailable.");
        }
    }

    public AIResponseDTO generateQuiz(AIRequestDTO request) {

        long start = System.currentTimeMillis();

        try {

            String response;

            if (request.getProvider() == ProviderType.GEMINI) {

                response = geminiProvider.generateQuiz(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            if (request.getProvider() == ProviderType.GROQ) {

                response = groqProvider.generateQuiz(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            // AUTO MODE
            try {

                response = geminiProvider.generateQuiz(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();

            } catch (Exception ex) {

                response = groqProvider.generateQuiz(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(true)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

        } catch (Exception ex) {

            throw new RuntimeException("Both AI providers are unavailable.");
        }
    }

    public AIResponseDTO askQuestion(AIRequestDTO request) {

        long start = System.currentTimeMillis();

        try {

            String response;

            if (request.getProvider() == ProviderType.GEMINI) {

                response = geminiProvider.askQuestion(
                        request.getText(),
                        request.getQuestion()
                );

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            if (request.getProvider() == ProviderType.GROQ) {

                response = groqProvider.askQuestion(
                        request.getText(),
                        request.getQuestion()
                );

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            // AUTO MODE
            try {

                response = geminiProvider.askQuestion(
                        request.getText(),
                        request.getQuestion()
                );

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();

            } catch (Exception ex) {

                response = groqProvider.askQuestion(
                        request.getText(),
                        request.getQuestion()
                );

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(true)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

        } catch (Exception ex) {

            ex.printStackTrace();

            throw new RuntimeException(ex);

        }
    }

    public AIResponseDTO generateKnowledgeGraph(AIRequestDTO request) {

        long start = System.currentTimeMillis();

        try {

            String response;

            // GEMINI
            if (request.getProvider() == ProviderType.GEMINI) {

                response = geminiProvider.generateKnowledgeGraph(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            // GROQ
            if (request.getProvider() == ProviderType.GROQ) {

                response = groqProvider.generateKnowledgeGraph(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

            // AUTO MODE
            try {

                response = geminiProvider.generateKnowledgeGraph(request.getText());

                return AIResponseDTO.builder()
                        .provider("Gemini")
                        .response(response)
                        .fallbackUsed(false)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();

            } catch (Exception ex) {

                response = groqProvider.generateKnowledgeGraph(request.getText());

                return AIResponseDTO.builder()
                        .provider("Groq")
                        .response(response)
                        .fallbackUsed(true)
                        .processingTime(System.currentTimeMillis() - start)
                        .build();
            }

        } catch (Exception ex) {

            throw new RuntimeException("Both AI providers are unavailable.");

        }
    }
}