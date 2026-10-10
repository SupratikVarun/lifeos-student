package com.lifeos.student.ai.provider.gemini;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.lifeos.student.ai.provider.AIProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiProvider implements AIProvider {

    private final Client geminiClient;

    private static final String MODEL = "gemini-2.5-flash";

    @Override
    public String getProviderName() {
        return "GEMINI";
    }

    @Override
    public String summarize(String text) {
        return generateResponse(
                "Summarize the following notes in clear bullet points:\n\n" + text
        );
    }

    @Override
    public String explain(String text) {
        return generateResponse(
                "Explain the following topic in simple language for a college student:\n\n" + text
        );
    }

    @Override
    public String generateFlashcards(String text) {
        return generateResponse(
                "Create 10 flashcards (Question - Answer) from:\n\n" + text
        );
    }

    @Override
    public String generateQuiz(String text) {
        return generateResponse(
                "Create a 10-question MCQ quiz with answers from:\n\n" + text
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
            GenerateContentResponse response =
                    geminiClient.models.generateContent(
                            MODEL,
                            prompt,
                            null
                    );

            return response.text();

        } catch (Exception e) {
            throw new RuntimeException("Gemini API Error: " + e.getMessage(), e);
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