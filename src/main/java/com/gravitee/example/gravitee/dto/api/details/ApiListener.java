package com.gravitee.example.gravitee.dto.api.details;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiListener {
    @Nullable
    private List<String> servers;
    public ListenerType type;
    public List<ListenerEntrypoint> entrypoints;

    // Host + Port
    @Nullable
    private String host;
    @Nullable
    private Integer port;

    // Hosts
    @Nullable
    private List<String> hosts;

    // Paths
    @Nullable
    private List<Path> paths;
    @Nullable
    private List<String> pathMappings;
    @Nullable
    private Cors cors;

}
