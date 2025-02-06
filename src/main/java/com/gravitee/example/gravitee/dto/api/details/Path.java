package com.gravitee.example.gravitee.dto.api.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Path {
    private String host;
    private String path;
    private Boolean overrideAccess;
}
