package com.gravitee.example.gravitee.dto.api.details;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHealthCheck {
    @Nullable
    private Boolean overrideConfiguration;
    @Nullable
    private EndpointConfiguration configuration;
    // default is true
    @Nullable
    private Boolean enabled;
    @Nullable
    private String type;

}
