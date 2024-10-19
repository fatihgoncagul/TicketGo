package com.example.loggingservice.service;


import com.example.loggingservice.model.LogEntry;
import com.example.loggingservice.repository.MongoLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service

public class LoggingService {


    private final MongoLogRepository mongoLogRepository;

    public LoggingService( MongoLogRepository mongoLogRepository) {
        this.mongoLogRepository = mongoLogRepository;
    }


    public ResponseEntity<String> addLog(@RequestBody LogEntry logEntry) {
        mongoLogRepository.save(logEntry);
        return new ResponseEntity<>("Log is saved to MongoDB",HttpStatus.OK );
    }

}
