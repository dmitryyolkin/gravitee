package com.gravitee.example.gravitee.dto.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pagination {
    private Integer page;
    private Integer perPage;
    private Integer pageCount;
    private Integer pageItemsCount;
    private Integer totalCount;
}
