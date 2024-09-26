package com.example.apigateway.service;


import com.example.apigateway.client.UserClient;
import com.example.apigateway.model.UserPrincipal;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailServiceImpl implements ReactiveUserDetailsService {

    private final UserClient userClient;

    public UserDetailServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }




    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userClient.getUserByUserName(username)
                .map(userDto -> (UserDetails) new UserPrincipal(userDto))
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("user not found")));
    }
}
