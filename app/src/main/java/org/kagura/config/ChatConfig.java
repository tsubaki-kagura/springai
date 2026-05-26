package org.kagura.config;

import org.kagura.advisor.UsageInfoAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatConfig {

    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .build();
    }

    @Bean
    public ChatClient deepseekChatClient(DeepSeekChatModel chatModel, ChatMemory chatMemory) {
        List<Advisor> advisors = List.of(UsageInfoAdvisor.getInstance(),
                MessageChatMemoryAdvisor.builder(chatMemory).build());
        return ChatClient.builder(chatModel).defaultAdvisors(advisors).build();
    }
}
