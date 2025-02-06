package com.gravitee.example.gravitee.dto.api.details;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cors {
    @Nullable
    public Boolean allowCredentials;
    @Nullable
    public List<String> allowHeaders;
    @Nullable
    public List<String> allowMethods;
    @Nullable
    public List<String> allowOrigin;
    @Nullable
    public Boolean enabled;
    @Nullable
    public List<String> exposeHeaders;
    @Nullable
    public Integer maxAge;
    @Nullable
    public Boolean runPolicies;
}
