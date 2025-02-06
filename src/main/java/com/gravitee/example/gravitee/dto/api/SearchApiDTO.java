package com.gravitee.example.gravitee.dto.api;

import com.gravitee.example.gravitee.dto.api.details.DefinitionVersion;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchApiDTO {
    private String query;
    private List<String> ids;
    private DefinitionVersion definitionVersion;

    public SearchApiDTO(String query) {
        this.query = query;
        this.ids = List.of();
        this.definitionVersion = DefinitionVersion.V4;
    }
}
