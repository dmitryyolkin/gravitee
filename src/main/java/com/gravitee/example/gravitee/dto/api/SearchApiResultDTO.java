package com.gravitee.example.gravitee.dto.api;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchApiResultDTO {
    private List<ApiDetailsDTO> data;
    private Pagination pagination;

}
