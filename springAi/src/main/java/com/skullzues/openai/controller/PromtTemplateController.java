package com.skullzues.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/apis")
public class PromtTemplateController {
    private static ChatClient ollamaEmailClient;

    public PromtTemplateController(@Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.ollamaEmailClient = ollamaChatClient;
    }

    @Value(
            "classpath:/promtTemplate/userPromtTemplate.st"
    )
    Resource userPromptTemplate;

    @GetMapping("ollama/email")
    public org.springframework.http.ResponseEntity<String> ollamaChat(@RequestParam("customerName") String customerName,
                                                              @RequestParam("customerMessage") String customerMessage){

        String s="";
        try{s =ollamaEmailClient.prompt()
                .system("""
                        You are a professional customer service assistant which helps drafting email
                        responses to improve the productivity of the customer support team
                        """)
                .user(promptTemplateSpec ->
                        promptTemplateSpec.text(userPromptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage))
                .call().content();}
        catch (Exception e){
            System.out.println(e.getCause());
        }
        return ResponseEntity.ok(s);
    }
}
