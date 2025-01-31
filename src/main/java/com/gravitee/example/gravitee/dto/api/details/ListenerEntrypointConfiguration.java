package com.gravitee.example.gravitee.dto.api.details;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// It can be extended for further cases
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListenerEntrypointConfiguration {
    private String host;
    private List<Path> paths;
}
