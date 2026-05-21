package org.kagura.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.template.TemplateRenderer;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.context.annotation.Bean;

public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, TemplateRenderer templateRenderer) {
        return chatClientBuilder.defaultTemplateRenderer(templateRenderer)
                .build();
    }

    @Bean
    public TemplateRenderer templateRenderer() {
        return StTemplateRenderer.builder()
                .startDelimiterToken('<')
                .endDelimiterToken('>')
                .build();
    }
}
