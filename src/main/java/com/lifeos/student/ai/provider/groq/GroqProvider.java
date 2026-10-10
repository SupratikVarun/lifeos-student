package com.lifeos.student.ai.provider.groq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeos.student.ai.provider.AIProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class GroqProvider implements AIProvider {


    private final HttpClient groqHttpClient;

    @Qualifier("groqApiKey")
    private final String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MODEL = "openai/gpt-oss-20b";

    @Override
    public String getProviderName() {
        return "GROQ";
    }

    @Override
    public String summarize(String text) {
        return generateResponse(
                "Summarize these notes in clear bullet points:\n\n" + text
        );
    }


    @Override
    public String explain(String text) {

        System.out.println("Explain method called");

        return generateResponse(
                "Explain this topic in simple language:\n\n" + text
        );
    }

    @Override
    public String generateFlashcards(String text) {

        System.out.println("Flashcards method called");

        return generateResponse(
                "Create 10 flashcards from:\n\n" + text
        );
    }

    @Override
    public String generateQuiz(String text) {

        System.out.println("Quiz method called");

        return generateResponse(
                "Create a 10-question MCQ quiz with answers:\n\n" + text
        );
    }

    @Override
    public String askQuestion(String context, String question) {

        return generateResponse("""
You are an AI tutor for college students.

Answer ONLY using the information from the uploaded PDF.

Rules:
- Use simple English.
- Keep answers concise.
- Format the answer in Markdown.
- Use headings.
- Use bullet points.
- Highlight important terms using **bold**.
- Give examples only if they exist in the PDF.
- Never copy large portions of the PDF.

If the user asks "Summarize", use this format:

## 📄 Overview

## 📌 Key Concepts

## 📖 Important Definitions

## 🎯 Exam Points

If the answer is NOT available in the uploaded PDF, reply exactly:

"This information is not available in the uploaded document."

-------------------------
PDF Content:
""" + context + """

-------------------------
User Question:
""" + question);
    }

    private String generateResponse(String prompt) {

        try {

            System.out.println("Step 1");

            String requestBody =
                    """
                    {
                      "model":"%s",
                      "messages":[
                        {
                          "role":"user",
                          "content":%s
                        }
                      ]
                    }
                    """.formatted(
                            MODEL,
                            objectMapper.writeValueAsString(prompt)
                    );

            System.out.println("Step 2");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            System.out.println("Step 3");

            HttpResponse<String> response =
                    groqHttpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println("Step 4");

            System.out.println(response.body());

            JsonNode json = objectMapper.readTree(response.body());

            System.out.println("Step 5");

            return json.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
    @Override
    public String generateKnowledgeGraph(String text) {

        return generateResponse("""
You are an expert Knowledge Graph generator.

Analyze the study material and extract the important concepts.

Return ONLY valid JSON.

DO NOT return markdown.

DO NOT return explanations.

DO NOT return ```json.

Use ONLY these node types:

SUBJECT
TOPIC
CONCEPT

Use ONLY these relation types:

CONTAINS
INCLUDES
RELATED_TO

Return EXACTLY this JSON format:

{
  "nodes": [
    {
      "name": "Database",
      "type": "SUBJECT"
    },
    {
      "name": "Normalization",
      "type": "TOPIC"
    },
    {
      "name": "1NF",
      "type": "CONCEPT"
    }
  ],
  "relations": [
    {
      "source": "Database",
      "target": "Normalization",
      "relation": "CONTAINS"
    },
    {
      "source": "Normalization",
      "target": "1NF",
      "relation": "INCLUDES"
    }
  ]
}

Every node must be unique.

Every relation must use one of:
CONTAINS
INCLUDES
RELATED_TO

Study Material:

""" + text);

    }
    }