package com.gravitee.example.gravitee.dto.api.details.pages;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageDetailsDocumentationDTO extends PageDocumentationDTO {
    private String id;
    private Integer order;
    private LocalDateTime updatedAt;
    private Boolean published;
}
