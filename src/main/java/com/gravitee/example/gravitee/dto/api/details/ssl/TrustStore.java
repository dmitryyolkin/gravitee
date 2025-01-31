package com.gravitee.example.gravitee.dto.api.details.ssl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrustStore {
    private TrustStoreType type;

    private String path;
    private String content;
    private String password;
}
