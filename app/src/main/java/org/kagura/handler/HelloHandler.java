package org.kagura.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloHandler {
    private final ChatClient chatClient;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return request.bind(UserChatRequest.class)
                .map(UserChatRequest::message)
                .flatMapMany(this::chat)
                .as(result -> ServerResponse.ok().body(result, String.class));
    }

    private Flux<String> chat(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .chatResponse()
                .doOnNext(chatResponse -> {
                    Usage usage = chatResponse.getMetadata().getUsage();
                    if (usage instanceof DefaultUsage defaultUsage) {
                        log.debug("default usage: {}", defaultUsage);
                    }
                })
                .mapNotNull(ChatResponse::getResult)
                .mapNotNull(result -> result.getOutput().getText());
    }

    private record UserChatRequest(String message) {
    }
}
