package com.techmanage.user.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record StandardError(
        @Schema(description = "Momento em que o erro ocorreu", example = "2025-02-22T15:30:00Z")
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        OffsetDateTime timestamp,

        @Schema(description = "Código do status HTTP", example = "400")
        Integer status,

        @Schema(description = "Tipo do erro", example = "Invalid parameters")
        String error,

        @Schema(description = "Descricao do erro", example = "One or more fields are invalid. Please fill them out correctly and try again.")
        String message,

        @Schema(description = "Caminho em que o erro ocorreu.", example = "/api/users")
        String path,

        @Schema(description = "Campos inválidos informados")
        List<ErrorFields> objects) {

        public record ErrorFields(
                @Schema(description = "Campos inválido informado", example = "name")
                String field,

                @Schema(description = "Descrição do valor inválido informado", example = "must not be blank.")
                String message
        ) {}
}
