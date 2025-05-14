package com.ead.course.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponse> handleNotFoundException(NotFoundException ex) {
        var errorResponse = new ErroResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        log.error("NotFoundException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        var errorResponse = new ErroResponse(HttpStatus.BAD_REQUEST.value(), "Error Validation failed", errors);
        log.error("MethodArgumentNotValidException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)

    public ResponseEntity<ErroResponse> handlerMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        var errorResponse = new ErroResponse(HttpStatus.BAD_REQUEST.value(), "Error: Invalid ID", null);
        log.error("MethodArgumentTypeMismatchException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> handleIvalidFormatException(HttpMessageNotReadableException ex) {
        Map<String, String> erros = new HashMap<>();
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            if (invalidFormatException.getTargetType() != null && invalidFormatException.getTargetType().isEnum()) {
                String FieldName = invalidFormatException.getPath().get(invalidFormatException.getPath().size() -1).getFieldName();
                String errorMessage = ex.getMessage();
                erros.put(FieldName, errorMessage);
            }
        }
        var errorResponse = new ErroResponse(HttpStatus.BAD_REQUEST.value(), "Error: Invalid enum value", erros);
        log.error("HttpMessageNotReadableException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
