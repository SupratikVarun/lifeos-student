package com.lifeos.student.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class GroqConfig {

    @Value("${groq.api.key}")
    private String apiKey;

    @Bean
    public HttpClient groqHttpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public String groqApiKey() {
        return apiKey;
    }
}