package com.gravitee.example.gravitee;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class GraviteeConfig {
    @Value("${gravitee.scheme}")
    private String scheme;
    @Value("${gravitee.host}")
    private String host;
    @Value("${gravitee.port}")
    private int port;
    @Value("${gravitee.organization}")
    private String organization;
    @Value("${gravitee.environment}")
    private String env;
    @Value("${gravitee.apy-key}")
    private String apiKey;

    @Value("${gravitee.urls.api.create}")
    private String createApiUrl;
    @Value("${gravitee.urls.api.update}")
    private String updateApiUrl;
    @Value("${gravitee.urls.api.search}")
    private String searchApiUrl;
    @Value("${gravitee.urls.api.start}")
    private String startApiUrlTemplate;
    @Value("${gravitee.urls.api.plan.create}")
    private String createApiPlanUrlTemplate;
    @Value("${gravitee.urls.api.plan.publish}")
    private String publishApiPlanUrlTemplate;

}
