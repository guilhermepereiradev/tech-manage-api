package com.techmanage.user.api.exceptionhandler;

import com.techmanage.user.domain.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        var error = "Entity not found";
        var status = HttpStatus.NOT_FOUND.value();
        var standardError = new StandardError(OffsetDateTime.now(), status, error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }
}
