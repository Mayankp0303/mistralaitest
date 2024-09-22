package com.example.learnai.mistralaitest.controller;

import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.learnai.mistralaitest.chatmodel.Mistralmodel;

@Configuration
public class Config {
    
    @Bean
    public MistralAiApi mistralAiApi(@Value("${spring.ai.mistralai.api-key}") String apiKey) {
        return new MistralAiApi(apiKey); // Replace "test" with your actual API key
    }

    @Bean
    //@Primary
    public Mistralmodel chatModel(MistralAiApi mistralAiApi) {
        return new Mistralmodel(mistralAiApi);
    }
}
