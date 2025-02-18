package com.gravitee.example.controller;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OpenApiExtractor {
    private final SpringDocConfigProperties springDocConfigProperties;
    private final RestTemplate restTemplate;

    @Autowired
    public OpenApiExtractor(SpringDocConfigProperties springDocConfigProperties,
                            RestTemplate restTemplate) {
        this.springDocConfigProperties = springDocConfigProperties;
        this.restTemplate = restTemplate;
    }

    @Nullable
    public String getOpenApiSchema(String contextPath) {
        String openApiPath = springDocConfigProperties.getApiDocs().getPath();
        ResponseEntity<String> response = restTemplate.getForEntity(
                String.format("http://localhost:8080%s", openApiPath),
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        log.error("Can't extract openApi schema for {}: {}", contextPath, response);
        return null;
    }

}
