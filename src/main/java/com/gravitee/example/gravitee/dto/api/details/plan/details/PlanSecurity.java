package com.gravitee.example.gravitee.dto.api.details.plan.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanSecurity {
    private PlanSecurityType type;
    private PlanSecurityConfig configuration;
}
