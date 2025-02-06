package com.gravitee.example.gravitee.dto.api.details.ssl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SslHeader {
    private String name;
    private String value;
}
