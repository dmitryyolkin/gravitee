package com.gravitee.example.gravitee.dto.api.details;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endpoint {
    @Nullable
    public String name;
    public String type;
    // default = 1
    @Nullable
    public Integer weight;
    @Nullable
    public Boolean inheritConfiguration;
    @Nullable
    public EndpointConfiguration configuration;
    @Nullable
    public EndpointGroupSharedConfiguration sharedConfigurationOverride;
    @Nullable
    public EndpointServices services;
    public Boolean secondary;
    public List<String> tenants;
}
