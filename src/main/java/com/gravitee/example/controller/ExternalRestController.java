package com.gravitee.example.controller;

public interface ExternalRestController {

    String getName();

    default String getVersion() {
        return "1.0.0";
    }

    String getContextPath();

}
