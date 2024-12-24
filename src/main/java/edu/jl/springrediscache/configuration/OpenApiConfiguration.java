package edu.jl.springrediscache.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Redis Cache API")
                        .version("1.1.0")
                        .description("API documentation for Spring Redis Cache application")
                        .license(new License().name("MIT license").url("https://opensource.org/license/mit")));
    }
}