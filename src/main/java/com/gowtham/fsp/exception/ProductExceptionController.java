package com.gowtham.fsp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionController {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> exception(ProductNotFoundException exception) {
        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ProductAlreadyExistsException.class)
    public ResponseEntity<Object> exception(ProductAlreadyExistsException exception) {
        return new ResponseEntity<>("Product Already Exists", HttpStatus.CONFLICT);
    }
}
