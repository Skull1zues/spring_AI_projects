package com.skullzues.openai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class StreamController {
    private ChatClient openAichatClient;
    private ChatClient ollamaChatClient;

    public StreamController(@Qualifier("openAiChatClient") ChatClient openAichatClient,
                          @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.openAichatClient = openAichatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/ollama/stream")
    public Flux<String> ollamaChat(@RequestParam("message") String message){

        return ollamaChatClient.prompt(message).stream().content();
    }
}
