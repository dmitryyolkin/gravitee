package com.gravitee.example.gravitee.dto.api;

import com.gravitee.example.gravitee.dto.api.details.ApiType;
import com.gravitee.example.gravitee.dto.api.details.DefinitionVersion;
import com.gravitee.example.gravitee.dto.api.details.EndpointGroup;
import com.gravitee.example.gravitee.dto.api.details.ApiListener;
import com.gravitee.example.gravitee.dto.api.details.Visibility;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// https://gravitee-io-labs.github.io/mapi-v2-docs-openapi-apis/#tag/apis/get/environments/{envId}/apis

@Data
@NoArgsConstructor
@SuperBuilder
public class ApiDTO {
    private String name;
    private String apiVersion;
    @Nullable
    private String description;
    private DefinitionVersion definitionVersion;
    @Nullable
    private List<String> groups;
    private ApiType type;
    private List<String> tags;
    private List<ApiListener> listeners;
    private List<EndpointGroup> endpointGroups;
    private Visibility visibility;

}
