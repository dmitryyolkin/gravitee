package com.gravitee.example.gravitee.dto.api.details.plan;

import com.gravitee.example.gravitee.dto.api.details.plan.details.PlanMode;
import com.gravitee.example.gravitee.dto.api.details.plan.details.PlanStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlanDetailsDTO extends PlanDTO {
    private String id;
    private String apiId;
    private PlanMode mode;
    private PlanStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;

}
