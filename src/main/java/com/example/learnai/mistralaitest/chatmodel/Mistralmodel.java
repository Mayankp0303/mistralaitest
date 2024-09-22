package com.example.learnai.mistralaitest.chatmodel;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class Mistralmodel implements ChatModel {

    private final MistralAiApi mistralAiApi;

    @Value("${spring.ai.mistralai.chat.options.model}")
    private String chatModel;

    
    public Mistralmodel(MistralAiApi mistralAiApi) {
        this.mistralAiApi = mistralAiApi;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        MistralAiChatOptions options = MistralAiChatOptions.builder()
                .withModel(chatModel)
                .withTemperature(0.5f)
                .build();

        ChatModel ch = new MistralAiChatModel(mistralAiApi);
        ChatResponse response = ch.call(prompt);

        System.out.println("Response: " + response.toString());
        return response;
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return ChatOptionsBuilder.builder()
                .withModel(chatModel)
                .build();
    }


    @Override
    public Flux<ChatResponse> stream(Prompt prompt){
        MistralAiChatOptions options = MistralAiChatOptions.builder()
                .withModel(chatModel)
                .withTemperature(0.5f)
                .build();

        ChatModel chm = new MistralAiChatModel(mistralAiApi);
        Flux<ChatResponse> fx = chm.stream(prompt);


        fx.subscribe(
            response -> System.out.println("Streamed response: {}"+ response),
            error -> System.out.println("Error during streaming responses"+ error)
        );
        System.out.println("test fx responmse" + fx );

        return fx;
    }
}
