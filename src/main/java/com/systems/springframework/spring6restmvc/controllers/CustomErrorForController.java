package com.systems.springframework.spring6restmvc.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorForController {

    @ExceptionHandler
    ResponseEntity handleJPAViolation(TransactionSystemException transactionSystemException){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
        if (transactionSystemException.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException ve= (ConstraintViolationException) transactionSystemException.getCause().getCause();
            List errors = ve.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        Map<String, String> stringMapError= new HashMap<>();
                        stringMapError.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                        return  stringMapError;
                    }).collect(Collectors.toList());
            return responseEntity.body(errors);
        }
        return responseEntity.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindException(MethodArgumentNotValidException methodArgumentNotValidException){
        List errorMesaagesList=
                methodArgumentNotValidException.getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, String> errorMessage= new HashMap<>();
                    errorMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMessage;
                }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorMesaagesList);
    }
}
