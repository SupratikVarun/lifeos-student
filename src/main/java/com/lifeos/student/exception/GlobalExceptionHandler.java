package com.lifeos.student.exception;

import com.lifeos.student.dto.AuthResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AuthResponseDTO> handleRuntimeException(RuntimeException ex) {

        AuthResponseDTO response = new AuthResponseDTO(
                false,
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDTO> handleException(Exception ex) {

        ex.printStackTrace();

        AuthResponseDTO response = new AuthResponseDTO(
                false,
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}