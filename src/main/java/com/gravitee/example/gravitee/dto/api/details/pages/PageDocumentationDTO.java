package com.gravitee.example.gravitee.dto.api.details.pages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDocumentationDTO {
    private String name;
    private PageDocumentationType type;
    private String content;
    private PageDocumentationVisibility visibility;

}
