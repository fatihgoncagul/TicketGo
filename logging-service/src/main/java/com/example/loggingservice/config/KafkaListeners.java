package com.example.loggingservice.config;

import com.example.loggingservice.model.LogEntry;
import com.example.loggingservice.repository.MongoLogRepository;
import com.example.loggingservice.service.LoggingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {


    private final ObjectMapper objectMapper;
    private final LoggingService loggingService;

    public KafkaListeners(MongoLogRepository mongoLogRepository, ObjectMapper objectMapper, LoggingService loggingService) {
        this.objectMapper = objectMapper;
        this.loggingService = loggingService;
    }

    @KafkaListener(topics = "logs", groupId = "exceptions")
    public void listener(String data) {
        try {
            // JSON verisini LogEntry objesine deserialize et
            LogEntry logEntry = objectMapper.readValue(data, LogEntry.class);

            // MongoDB'ye kaydet
            loggingService.addLog(logEntry);

            System.out.println("Log saved to MongoDB: " + logEntry);
        } catch (Exception e) {
            System.err.println("Error processing log: " + e.getMessage());
        }
    }
}



