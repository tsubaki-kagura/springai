package org.kagura.config;

import org.kagura.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(HelloHandler helloHandler) {
        return RouterFunctions.route()
                .GET("/hello", helloHandler::hello)
                .build();
    }
}
