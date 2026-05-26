package org.kagura.config;

import org.kagura.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(HelloHandler helloHandler) {
        return RouterFunctions.route()
                .GET("/hello", helloHandler::hello)
                .build();
    }
}
