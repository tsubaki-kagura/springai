package org.kagura.config;

import lombok.RequiredArgsConstructor;
import org.kagura.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
public class RouterConfig {
    private final HelloHandler helloHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .path("/hello", builder -> builder
                        .GET("/deepseek", helloHandler::deepseekChat)
                        .GET("/ollama", helloHandler::ollamaChat))
                .build();
    }
}
