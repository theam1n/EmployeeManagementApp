package com.example.demo.handler;

import com.example.demo.dto.ErrorDto;
import com.example.demo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> dataNotFound(NotFoundException ex) {

        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(ErrorDto.builder()
                        .status(status)
                        .message(ex.getMessage())
                        .build());
    }
}
