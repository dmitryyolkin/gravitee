package com.gravitee.example.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gravitee.example.controller.HelloWorldController.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Examples")
@Validated
public interface HelloWorldApi extends ExternalRestController {

    String HELLO_WORLD_CONTEXT_PATH = "/hello-world";

    @Operation(summary = "Hello ApiDTO example",
            operationId = "helloWorld", responses =
            {
                    @ApiResponse(responseCode = "200", description = "All is ok",
                            content = @Content(array =
                            @ArraySchema(schema = @Schema(implementation = HelloWorldController.Response.class))
                            )
                    ),
            })
    @GetMapping(
            value = HELLO_WORLD_CONTEXT_PATH,
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Response> helloWorld(HttpServletRequest httpServletRequest);

    @Override
    default String getName() {
        return "Hello-world";
    }

    @Override
    default String getContextPath() {
        return HELLO_WORLD_CONTEXT_PATH;
    }
}
