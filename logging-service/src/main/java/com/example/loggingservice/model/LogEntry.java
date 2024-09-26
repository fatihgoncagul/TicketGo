package com.example.loggingservice.model;

import lombok.Data;

import org.springframework.boot.logging.LogLevel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document
public class LogEntry {

    private LocalDateTime timeStamp;
    private LogLevel level;
    private String serviceName;
    private String message;


}
