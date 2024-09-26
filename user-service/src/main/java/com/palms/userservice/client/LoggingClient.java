package com.palms.userservice.client;

import com.palms.userservice.dto.LogDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("logging-service")
public class LoggingClient {

    @PostMapping("log/getLog")
    public String sendLog(@RequestBody LogDto logDto) {

        return "Log has been sent to the logging service";
    }

}
