package com.skullzues.openai.controller;

import com.skullzues.openai.advisor.TokenUsagesAuditAdvisor;
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
public class PromtStuffingController {
    private static ChatClient ollamaEmailClient;

    public PromtStuffingController(@Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.ollamaEmailClient = ollamaChatClient;
    }

    @Value(
            "classpath:/promtTemplate/systemPromptStuffing.st"
    )
    Resource userPromptTemplate;

    @GetMapping("ollama/stuffing")
    public ResponseEntity<String> ollamaChat(@RequestParam("message") String message){

        String s="";
        try{s =ollamaEmailClient.prompt()
                //.advisors(new TokenUsagesAuditAdvisor())
                .system(userPromptTemplate)
                .user(message)
                .call().content();}
        catch (Exception e){
            System.out.println(e.getCause());
        }
        return ResponseEntity.ok(s);
    }
}
