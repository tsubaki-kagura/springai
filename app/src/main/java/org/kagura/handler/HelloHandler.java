package org.kagura.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HelloHandler {
    private final ChatClient chatClient;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return request.bind(UserChatRequest.class)
                .flatMapMany(userChatRequest -> chatClient.prompt()
                        .user(userChatRequest.message)
                        .stream()
                        .content())
                .as(result -> ServerResponse.ok().body(result, String.class));
    }

    private record UserChatRequest(String message) {
    }
}
