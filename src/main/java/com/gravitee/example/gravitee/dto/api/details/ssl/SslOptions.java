package com.gravitee.example.gravitee.dto.api.details.ssl;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// https://gravitee-io-labs.github.io/mapi-v2-docs-openapi-apis/#model/httpclientssloptions

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SslOptions {
    private Boolean trustAll;
    private Boolean hostnameVerifier;
    private TrustStore trustStore;
    private KeyStore keyStore;
    private List<SslHeader> headers;
}
