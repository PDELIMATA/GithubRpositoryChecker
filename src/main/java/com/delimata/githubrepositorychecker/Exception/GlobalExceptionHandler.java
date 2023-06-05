package com.delimata.githubrepositorychecker.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<String> handleNotFoundException() {
        return createJsonErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found.");
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> handleNotAcceptableException() {
        return createJsonErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Unsupported media type");
    }

    private ResponseEntity<String> createJsonErrorResponse(int status, String message) {
        String jsonResponse = "{\"status\": " + status + ", \"message\": \"" + message + "\"}";
        return ResponseEntity.status(status).body(jsonResponse);
    }
}