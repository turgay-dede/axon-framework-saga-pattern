package com.turgaydede.command.api.errorhandling;


import com.turgaydede.result.ErrorResult;
import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class InventoryServiceErrorHandler {

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException exception, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(exception.getMessage());
        return new  ResponseEntity<>(errorResult, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception exception, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(exception.getMessage());
        return new  ResponseEntity<>(errorResult, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CommandExecutionException.class})
    public ResponseEntity<Object> handleCommandExecutionException(CommandExecutionException exception, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(exception.getMessage());
        return new  ResponseEntity<>(errorResult, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
