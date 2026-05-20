package org.kagura.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@Import(RouterConfig.class)
public class WebFluxConfig implements WebFluxConfigurer {
}
