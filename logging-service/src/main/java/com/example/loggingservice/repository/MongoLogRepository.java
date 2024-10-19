package com.example.loggingservice.repository;

import com.example.loggingservice.model.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoLogRepository extends MongoRepository<LogEntry, String> {
}
