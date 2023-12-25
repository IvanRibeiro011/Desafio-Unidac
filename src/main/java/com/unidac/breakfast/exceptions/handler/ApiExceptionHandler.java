package com.unidac.breakfast.exceptions.handler;

import com.unidac.breakfast.exceptions.ResourceNotFoundException;
import com.unidac.breakfast.exceptions.errors.ApiErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        Integer status = HttpStatus.NOT_FOUND.value();
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage();
        apiErrorMessage.setStatus(status);
        apiErrorMessage.setTimestamp(LocalDateTime.now());
        apiErrorMessage.setError("Resource not found");
        apiErrorMessage.setMessage(e.getMessage());
        apiErrorMessage.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(apiErrorMessage);
    }
}
