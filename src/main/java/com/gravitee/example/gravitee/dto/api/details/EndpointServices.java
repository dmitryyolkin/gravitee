package com.gravitee.example.gravitee.dto.api.details;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointServices {
    @Nullable
    private EndpointDiscovery discovery;
    @Nullable
    private EndpointHealthCheck healthCheck;
}
