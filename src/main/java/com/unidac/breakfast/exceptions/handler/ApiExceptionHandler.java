package com.unidac.breakfast.exceptions.handler;

import com.unidac.breakfast.exceptions.ItemAlreadyRegisteredException;
import com.unidac.breakfast.exceptions.ResourceNotFoundException;
import com.unidac.breakfast.exceptions.errors.ApiArgumentNotValidMessage;
import com.unidac.breakfast.exceptions.errors.ApiErrorMessage;
import com.unidac.breakfast.exceptions.errors.ApiFieldError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        int status = HttpStatus.NOT_FOUND.value();
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage();
        apiErrorMessage.setStatus(status);
        apiErrorMessage.setTimestamp(Instant.now());
        apiErrorMessage.setError("Resource not found");
        apiErrorMessage.setMessage(e.getMessage());
        apiErrorMessage.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(apiErrorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorMessage> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        int status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        ApiArgumentNotValidMessage message = new ApiArgumentNotValidMessage();
        message.setStatus(status);
        message.setTimestamp(Instant.now());
        message.setError("Validation Error");
        message.setMessage(e.getMessage());
        message.setPath(request.getRequestURI());

        for (FieldError fe : e.getFieldErrors()) {
            message.addError(new ApiFieldError(fe.getField(), fe.getDefaultMessage()));
        }

        return ResponseEntity.status(status).body(message);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiErrorMessage> sqlException(SQLException e, HttpServletRequest request) {
        int status = HttpStatus.NOT_FOUND.value();
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage();
        apiErrorMessage.setStatus(status);
        apiErrorMessage.setTimestamp(Instant.now());
        apiErrorMessage.setError("Database Exception");
        apiErrorMessage.setMessage(e.getMessage());
        apiErrorMessage.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(apiErrorMessage);
    }

    @ExceptionHandler(ItemAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorMessage> itemAlreadyRegistered(ItemAlreadyRegisteredException e, HttpServletRequest request) {
        int status = HttpStatus.UNAUTHORIZED.value();
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage();
        apiErrorMessage.setStatus(status);
        apiErrorMessage.setTimestamp(Instant.now());
        apiErrorMessage.setError("Item Already Registered Exception");
        apiErrorMessage.setMessage(e.getMessage());
        apiErrorMessage.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(apiErrorMessage);
    }
}
