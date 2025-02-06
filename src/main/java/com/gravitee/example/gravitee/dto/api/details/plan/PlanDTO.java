package com.gravitee.example.gravitee.dto.api.details.plan;

import com.gravitee.example.gravitee.dto.api.details.DefinitionVersion;
import com.gravitee.example.gravitee.dto.api.details.plan.details.PlanSecurity;
import com.gravitee.example.gravitee.dto.api.details.plan.details.PlanValidation;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {
    private String name;
    @Nullable
    private String description;
    private DefinitionVersion definitionVersion;
    private PlanValidation validation;
    private PlanSecurity security;
}
