package com.delimata.githubrepositorychecker.Exception;

import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DecodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleValidationException(DecodingException ex) {
        String message = ex.getMessage();
        return createJsonErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFoundException() {
        String message = "User not found ";
        return createJsonErrorResponse(HttpStatus.NOT_FOUND.value(), message);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public String handleMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        String message = "Unsupported media type: " + ex.getBody().getDetail();
        return createJsonErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), message);
    }

    private String createJsonErrorResponse(int status, String message) {
        return "{\"status\": " + status + ", \"message\": \"" + message + "\"}";
    }
}
