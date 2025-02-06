package com.gravitee.example;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                                     @Value("${rest.template.connection-timeout}") long connectionTimeout,
                                     @Value("${rest.template.read-timeout}") long readTimeout) {
        return restTemplateBuilder
                .connectTimeout(Duration.ofMillis(connectionTimeout))
                .readTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

}
