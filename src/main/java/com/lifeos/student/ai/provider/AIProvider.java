package com.lifeos.student.ai.provider;

public interface AIProvider {

    String getProviderName();

    String summarize(String text);

    String explain(String text);

    String generateFlashcards(String text);

    String generateQuiz(String text);

    String askQuestion(String context, String question);

    String generateKnowledgeGraph(String text);
}