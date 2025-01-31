package com.gravitee.example.gravitee.dto.api.details;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointGroup {
    @Nullable
    public String name;
    public String type;
    @Nullable
    public LoadBalancer loadBalancer;
    @Nullable
    public EndpointGroupSharedConfiguration sharedConfiguration;
    @Nullable
    public List<Endpoint> endpoints;
    @Nullable
    public EndpointServices services;
}
