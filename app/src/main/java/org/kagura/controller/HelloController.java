package org.kagura.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final ChatClient deepseekChatClient;

    @GetMapping("/hello")
    public ResponseEntity<Flux<String>> hello(String message) {
        return deepseekChatClient.prompt()
                .user(message)
                .stream()
                .chatResponse()
                .transform(this::enableThink)
                .as(ResponseEntity::ok);
    }

    private Flux<String> enableThink(Flux<ChatResponse> chatResponseFlux) {
        return chatResponseFlux.mapNotNull(ChatResponse::getResult)
                .mapNotNull(generation -> {
                    DeepSeekAssistantMessage message = (DeepSeekAssistantMessage) generation.getOutput();
                    String reasoningContent = message.getReasoningContent();
                    if (StringUtils.hasText(reasoningContent)) {
                        return reasoningContent;
                    }
                    String text = message.getText();
                    return StringUtils.hasText(text) ? text : null;
                });
    }
}
