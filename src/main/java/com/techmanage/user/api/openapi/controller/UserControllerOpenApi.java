package com.techmanage.user.api.openapi.controller;

import com.techmanage.user.api.dto.UserRequest;
import com.techmanage.user.api.dto.UserResponse;
import com.techmanage.user.api.exceptionhandler.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Usuários", description = "Gerencia os usuários")
public interface UserControllerOpenApi {

    @Operation(
            summary = "Lista de usuários",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Usuários encontrados",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            )
    )
    ResponseEntity<List<UserResponse>> findAllUsers();

    @Operation(
            summary = "Busca um usuário por ID",
            parameters = @Parameter(name = "id", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "timestamp": "2025-02-22T15:30:00Z",
                                                "status": 404,
                                                "error": "Entity not found",
                                                "message": "User not found with id 5",
                                                "path": "/api/users/5"
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "timestamp": "2025-02-22T15:30:00Z",
                                                "status": 400,
                                                "error": "Type mismatch",
                                                "message": "Method parameter 'id': Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; For input string: \\"abc\\"",
                                                "path": "/api/users/abc"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> findUserById(Long id);

    @Operation(
            summary = "Cadastra um usuário",
            parameters = @Parameter(name = "id", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário criado",
                            content = @Content(schema = @Schema(implementation = UserRequest.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida",
                            content = @Content(schema = @Schema(implementation = StandardError.class))
                    )
            }
    )
    ResponseEntity<UserResponse> createUser(UserRequest userRequest);


    @Operation(
            summary = "Remove um usuário",
            parameters = @Parameter(name = "id", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário removido"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "timestamp": "2025-02-22T15:30:00Z",
                                                "status": 404,
                                                "error": "Entity not found",
                                                "message": "User not found with id 5",
                                                "path": "/api/users/5"
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "timestamp": "2025-02-22T15:30:00Z",
                                                "status": 400,
                                                "error": "Type mismatch",
                                                "message": "Method parameter 'id': Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; For input string: \\"abc\\"",
                                                "path": "/api/users/abc"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteUser(Long id);


    @Operation(
            summary = "Atualiza um usuário",
            parameters = @Parameter(name = "id", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário atualizado",
                            content = @Content(schema = @Schema(implementation = UserRequest.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "timestamp": "2025-02-22T15:30:00Z",
                                                "status": 404,
                                                "error": "Entity not found",
                                                "message": "User not found with id 5",
                                                "path": "/api/users/5"
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida",
                            content = @Content(schema = @Schema(implementation = StandardError.class))
                    )
            }
    )
    ResponseEntity<UserResponse> updateUser(Long id, UserRequest userRequest);
}
