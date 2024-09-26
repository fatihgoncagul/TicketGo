package com.example.authservice.service;

import com.example.authservice.client.UserClient;
import com.example.authservice.dto.UserDto;
import com.example.authservice.model.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserClient userClient;

    public AuthService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        UserDto userDto = userClient.getUserByUserName(username).getBody();
        if (userDto ==null){
            System.out.println("User 404");
            throw new UsernameNotFoundException("User404");
        }


        return new UserPrincipal(userDto);
    }


}
