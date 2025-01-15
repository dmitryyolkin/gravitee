package com.gravitee.example.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HelloWorld API")
                        .version("1.0.0")
                        .summary("OpenApi example")
                        .contact(new Contact()
                                .name("My Company")
                                .url("https://my-company.com")
                                .email("dmitry.yolkin@gmail.com")
                        )
                );

    }

}

