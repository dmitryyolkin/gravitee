package com.gravitee.example.gravitee.dto.api.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// https://gravitee-io-labs.github.io/mapi-v2-docs-openapi-apis/#model/httpclientssloptions

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfiguration {
    private Integer keepAliveTimeout;
    private Boolean keepAlive;
    private Boolean followRedirects;
    private Integer readTimeout;
    private Integer idleTimeout;
    private Integer connectTimeout;
    private Boolean useCompression;
    private Integer maxConcurrentConnections;
    private HttpVersion version;
    private Boolean pipelining;
}
