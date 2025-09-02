package com.skullzues.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private ChatClient openAichatClient;
    private ChatClient ollamaChatClient;

    public ChatController(@Qualifier("openAiChatClient") ChatClient openAichatClient,
                         @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.openAichatClient = openAichatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/openai/chat")
    public String chat(@RequestParam("message") String message){

        return openAichatClient
                .prompt()
                /*.system("""
                        You are HR assistent. You only answer regarding HR related issues""")*/
                .user(message)
                .call().content();
    }

    @GetMapping("/ollama/chat")
    public String ollamaChat(@RequestParam("message") String message){

        return ollamaChatClient.prompt(message).call().content();
    }
}
