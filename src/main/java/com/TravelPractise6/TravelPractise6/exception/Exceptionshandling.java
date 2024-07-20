package com.TravelPractise6.TravelPractise6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class Exceptionshandling {

    @ExceptionHandler
    public ResponseEntity<?> propertyException(
            PropertyIdNotFoundException propertyIdNotFoundException,
            WebRequest request
    ){
        ErrorDetails errorDetails=new ErrorDetails();
        errorDetails.setErrormessage(propertyIdNotFoundException.getMessage());
        errorDetails.setLdt(LocalDate.now());
        errorDetails.setRequest(request.getDescription(true));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> reviewException(
            MultipleReviewsFoundException multipleReviewsFoundException
    ){

        return new ResponseEntity<>(multipleReviewsFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
