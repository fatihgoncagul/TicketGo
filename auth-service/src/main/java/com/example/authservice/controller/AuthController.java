package com.example.authservice.controller;

import com.example.authservice.dto.UserDto;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;


    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,  JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }



    @PostMapping("login")
    public String login(@RequestBody UserDto userDto){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUserName(),userDto.getPassword()));

        if (authentication.isAuthenticated()){
            return jwtService.generateToken(userDto);
        }
        else {
            return "Login failed";
        }
    }

}
