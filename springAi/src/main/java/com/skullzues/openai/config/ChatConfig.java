package com.skullzues.openai.config;

import com.skullzues.openai.advisor.TokenUsagesAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel){
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem("""
                        You are HR assistent. You only answer regarding HR related issues""").build();
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){

        ChatOptions chatOptions = ChatOptions.builder().model("llama3.2:3b").maxTokens(1000)
                .temperature(0.8).build();

        ChatClient.Builder chatClientBuilder =ChatClient.builder(ollamaChatModel)
                .defaultOptions(chatOptions)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsagesAuditAdvisor()))
                .defaultSystem("""
                        You are HR assistent. You only answer regarding HR related issues""");
        return chatClientBuilder.build();
    }

    @Bean
    public ChatClient normalOllamaChatClient(OllamaChatModel ollamaChatModel){

        ChatClient.Builder chatClientBuilder =ChatClient.builder(ollamaChatModel)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsagesAuditAdvisor()));
        return chatClientBuilder.build();
    }


}
