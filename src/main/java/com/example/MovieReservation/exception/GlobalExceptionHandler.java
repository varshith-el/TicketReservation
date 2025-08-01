package com.example.MovieReservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Resource Not Found");
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatAlreadyBookedException.class)
    public ResponseEntity<?> handleSeatAlreadyBookedException(SeatAlreadyBookedException ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Seat Already Booked");
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Invalid Request");
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", "An unexpected error occurred");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}