package com.gravitee.example.gravitee.dto.api.details;

import com.gravitee.example.gravitee.dto.api.details.ssl.SslOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointGroupSharedConfiguration {
    private ProxyConfiguration proxy;
    private HttpConfiguration http;
    private SslOptions ssl;
}
