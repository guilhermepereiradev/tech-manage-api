package com.techmanage.user.api.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

@Tag(name = "Status", description = "Verifica status da aplicação")
public interface StatusControllerOpenApi {

    @Operation(
            summary = "Verifica status da aplicação",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Status da aplição",
                    content = @Content(schema = @Schema(example = """
                                {
                                  "service": "tech-manage-api",
                                  "status": "success",
                                  "statusCode": "OK"
                                }
                            """))
            )
    )
    ResponseEntity<HashMap<String, Object>> getApiStatus();
}
