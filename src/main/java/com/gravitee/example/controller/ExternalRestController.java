package com.gravitee.example.controller;

import jakarta.annotation.Nullable;

public interface ExternalRestController {

    String getName();

    default String getVersion() {
        return "1.0.0";
    }

    String getContextPath();

    @Nullable
    String getOpenApiSchema();

}
