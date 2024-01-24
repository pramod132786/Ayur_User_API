package com.anarghya.aayurvedaappuser.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Check if the exception message contains the specific constraint violation error
        if (ex.getMessage().contains("user_module.UK_5glvugeyqunbn858rf31ftc4c")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mobile Number that you're trying to register already exists. Please try with a different Mobile Number.");
        }
        if (ex.getMessage().contains("user_module.UK_3e3cx90wjb5ren7hjbervivg")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email that you're trying to register already exists. Please try with a different Email.");
        }

        // Handle other DataIntegrityViolationException cases if needed
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        result.getFieldErrors().forEach(fieldError -> {
            errorMessages.add(fieldError.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body("Validation failed: " + String.join(", ", errorMessages));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessages.add(violation.getMessage());
        }

        return ResponseEntity.badRequest().body("Validation failed: " + String.join(", ", errorMessages));
    }
}


