package com.gravitee.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorldController implements HelloWorldApi {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    @Override
    public ResponseEntity<Response> helloWorld(HttpServletRequest httpServletRequest) {
        long timestamp = System.currentTimeMillis();
        log.debug("Invoke hello-world at " + timestamp);
        return new ResponseEntity<>(
                new Response(
                        timestamp,
                        "Hello world"
                ),
                HttpStatus.OK
        );
    }


    public record Response(long timestamp, String value) {
    }

}
