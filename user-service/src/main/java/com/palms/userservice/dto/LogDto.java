package com.palms.userservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class LogDto {


    private String message;
    private String exceptionType;
    private String serviceName;


    // Constructor, Getters, Setters
    public LogDto(String message, String exceptionType, String serviceName) {
        this.message = message;
        this.exceptionType = exceptionType;
        this.serviceName = serviceName;
    }

}
