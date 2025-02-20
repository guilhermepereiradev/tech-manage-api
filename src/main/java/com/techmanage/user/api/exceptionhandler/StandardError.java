package com.techmanage.user.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record StandardError(
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        OffsetDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path) {
}
