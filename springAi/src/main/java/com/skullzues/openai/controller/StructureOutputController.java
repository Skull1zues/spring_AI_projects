package com.skullzues.openai.controller;


import com.skullzues.openai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StructureOutputController {
    private ChatClient ollamaChatClient;

    public StructureOutputController(@Qualifier("normalOllamaChatClient") ChatClient ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> ollamaChat(@RequestParam("message") String message){

        CountryCities countryCities = ollamaChatClient.prompt()
                .user(message)
                .call()
                .entity(CountryCities.class);
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> ollamalist(@RequestParam("message") String message){

        List<String> countryCities = ollamaChatClient.prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-pojo")
    public ResponseEntity<List<CountryCities>> ollamapojo(@RequestParam("message") String message){

        List<CountryCities> countryCities = ollamaChatClient.prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        return ResponseEntity.ok(countryCities);
    }
}
