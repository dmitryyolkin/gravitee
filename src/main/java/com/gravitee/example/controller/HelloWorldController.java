package com.gravitee.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorldController implements HelloWorldApi {

    @Override
    public ResponseEntity<Response> helloWorld(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                new Response(
                        System.currentTimeMillis(),
                        "Hello world"
                ),
                HttpStatus.OK
        );
    }


    public record Response(long timestamp, String value) {
    }

}
