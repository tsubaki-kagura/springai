package org.kagura.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;

public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }
}
