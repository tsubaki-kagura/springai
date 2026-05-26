package org.kagura.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient deepseekChatClient(DeepSeekChatModel deepSeekChatModel, VectorStore vectorStore) {
        return ChatClient.builder(deepSeekChatModel)
                // .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
    }
}
