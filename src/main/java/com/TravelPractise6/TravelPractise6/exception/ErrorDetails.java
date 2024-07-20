package com.TravelPractise6.TravelPractise6.exception;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ErrorDetails {

    private String errormessage;
    private LocalDate ldt;
    private String request;
}
