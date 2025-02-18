package com.gravitee.example.gravitee.dto.api.details.pages;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPageDocumentationDetailsDTO {
    private List<PageDetailsDocumentationDTO> pages;
}
