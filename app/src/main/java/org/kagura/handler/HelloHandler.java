package org.kagura.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HelloHandler {
    private final ChatClient chatClient;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return request.bind(UserChatRequest.class)
                .flatMapMany(this::chat)
                .as(result -> ServerResponse.ok().body(result, String.class));
    }

    private Flux<String> chat(UserChatRequest userChatRequest) {
        return chatClient.prompt()
                .user(userChatRequest.message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, userChatRequest.uid.toString()))
                .stream()
                .content();
    }

    private record UserChatRequest(Integer uid, String message) {
    }
}
