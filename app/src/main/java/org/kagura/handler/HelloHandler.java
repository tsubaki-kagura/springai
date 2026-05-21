package org.kagura.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HelloHandler {
    private final ChatClient chatClient;

    @Value("classpath:prompts/system.st")
    private Resource system;

    @Value("classpath:prompts/user.st")
    private Resource user;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return request.bind(UserChatRequest.class)
                .flatMapMany(this::chat)
                .as(result -> ServerResponse.ok().body(result, Person.class));
    }

    private Flux<Person> chat(UserChatRequest userChatRequest) {
        return Mono.fromCallable(() -> chatClient.prompt()
                        .system(system, StandardCharsets.UTF_8)
                        .user(spec -> spec.text(user, StandardCharsets.UTF_8)
                                .param("count", userChatRequest.count))
                        .call()
                        .<List<Person>>entity(new ParameterizedTypeReference<>() {
                        }))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable);
    }

    private record UserChatRequest(Integer count) {
    }

    private record Person(String name, Integer age) {
    }
}
