package com.gravitee.example.gravitee.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gravitee.example.gravitee.dto.api.details.LifecycleState;
import com.gravitee.example.gravitee.dto.api.details.PrimaryOwner;
import com.gravitee.example.gravitee.dto.api.details.State;
import com.gravitee.example.gravitee.dto.api.details.Visibility;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiDetailsDTO extends ApiDTO {
    private String id;
    private LocalDateTime deployedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private State state;
    private LifecycleState lifecycleState;

    private PrimaryOwner primaryOwner;
}
