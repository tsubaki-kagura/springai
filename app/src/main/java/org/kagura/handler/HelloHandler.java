package org.kagura.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HelloHandler {

    @Qualifier("deepseekChatClient")
    private final ChatClient deepseekChatClient;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return request.bind(UserChatRequest.class)
                .flatMapMany(this::chat)
                .as(result -> ServerResponse.ok().body(result, String.class));
    }

    private Flux<String> chat(UserChatRequest userChatRequest) {
        return deepseekChatClient.prompt()
                .user(userChatRequest.message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, userChatRequest.uid.toString()))
                .stream()
                .content();
    }

    private record UserChatRequest(Integer uid, String message) {
    }
}
