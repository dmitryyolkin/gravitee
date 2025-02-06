package com.gravitee.example.gravitee.dto.api.details;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListenerEntrypoint {
    private String type;
    @Nullable
    private QoS qos;
    @Nullable
    private DQL dql;
    @Nullable
    private ListenerEntrypointConfiguration configuration;
}
