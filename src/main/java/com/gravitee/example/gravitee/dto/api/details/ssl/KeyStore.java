package com.gravitee.example.gravitee.dto.api.details.ssl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyStore {
    private KeyStoreType type;

    private String path;
    private String content;
    private String password;

    private String keyPath;
    private String keyContent;
    private String certPath;
    private String certContent;


}
