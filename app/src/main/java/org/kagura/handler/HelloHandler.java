package org.kagura.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HelloHandler {

    @Qualifier("deepseekChatClient")
    private final ChatClient deepseekChatClient;

    @Qualifier("ollamaChatClient")
    private final ChatClient ollamaChatClient;

    private Mono<ServerResponse> hello(ServerRequest request, ChatClient chatClient) {
        return request.bind(UserChatRequest.class)
                .flatMapMany(userChatRequest -> chatClient.prompt()
                        .user(userChatRequest.message)
                        .stream()
                        .content())
                .as(result -> ServerResponse.ok().body(result, String.class));
    }

    public Mono<ServerResponse> deepseekChat(ServerRequest request) {
        return hello(request, deepseekChatClient);
    }

    public Mono<ServerResponse> ollamaChat(ServerRequest request) {
        return hello(request, ollamaChatClient);
    }

    private record UserChatRequest(String message) {
    }
}
