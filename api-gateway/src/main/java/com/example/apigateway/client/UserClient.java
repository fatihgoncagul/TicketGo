package com.example.apigateway.client;


import com.example.apigateway.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component

public class UserClient  {

    private final WebClient webClient;

    public UserClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<UserDto> getUserByUserName(@PathVariable("userName") String userName){

        return this.webClient.get().uri("/user/{userName}",userName)
                .retrieve().bodyToMono(UserDto.class);

    }



}
