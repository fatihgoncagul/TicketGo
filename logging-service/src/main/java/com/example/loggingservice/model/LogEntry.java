package com.example.loggingservice.model;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class LogEntry {


    private String exceptionType;
    private String serviceName;
    private String message;

}
