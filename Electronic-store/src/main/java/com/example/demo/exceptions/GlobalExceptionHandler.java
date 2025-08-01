package com.example.demo.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

        ApiResponseMessage response = new ApiResponseMessage();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setSuccess(false);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    //for methodargument not valid exception 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();

        allErrors.forEach(error -> {
            String message = error.getDefaultMessage();
            String field = (error instanceof FieldError) ? ((FieldError) error).getField() : "unknown";
            response.put(field, message);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> handleFileTypeNotAllowed(BadApiRequest ex) {
        ApiResponseMessage message = new ApiResponseMessage();
        message.setMessage(ex.getMessage());
        message.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        message.setSuccess(false);
        return new ResponseEntity<>(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
