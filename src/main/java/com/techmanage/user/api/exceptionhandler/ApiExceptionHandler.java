package com.techmanage.user.api.exceptionhandler;

import com.techmanage.user.domain.exception.BusinessException;
import com.techmanage.user.domain.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND.value();
        var error = "Entity not found";
        return getErrorResponse(status, error, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e, HttpServletRequest request) {
        var error = "Invalid request";
        var status = HttpStatus.BAD_REQUEST.value();

        return getErrorResponse(status, error, e.getMessage(), request.getRequestURI());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        var error = "Invalid parameters";
        var message = "One or more fields are invalid. Please fill them out correctly and try again.";
        var servletRequest = ((ServletWebRequest) request).getRequest();

        var errorFields = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> new StandardError.ErrorFields(((FieldError) err).getField(), err.getDefaultMessage()))
                .toList();

        return getErrorResponse(status.value(), error, message, servletRequest.getRequestURI(), errorFields);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {
        var error = "Type mismatch";
        var servletRequest = ((ServletWebRequest) request).getRequest();

        return getErrorResponse(status.value(), error, ex.getMessage(), servletRequest.getRequestURI());
    }

    private ResponseEntity<Object> getErrorResponse(Integer status, String error, String message, String path) {
        return getErrorResponse(status, error, message, path, null);
    }

    private ResponseEntity<Object> getErrorResponse(Integer status, String error, String message, String path,
                                                    List<StandardError.ErrorFields> errorFields) {
        var standardError = new StandardError(OffsetDateTime.now(), status, error, message, path, errorFields);
        return new ResponseEntity<>(standardError, HttpStatus.valueOf(status));
    }
}
