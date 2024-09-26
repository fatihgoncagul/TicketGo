package com.example.authservice.client;

import com.example.authservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("user/{userName}")
    ResponseEntity<UserDto> getUserByUserName(@PathVariable("userName") String userName);



}
