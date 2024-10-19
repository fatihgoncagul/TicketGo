package com.palms.userservice.controller;

import com.palms.userservice.dto.UserDto;
import com.palms.userservice.exception.UserAlreadyExistsException;
import com.palms.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.palms.userservice.model.User;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user){
        Optional<User> existingUser = userService.findUserByUserName(user.getUserName());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with username: " + user.getUserName());
        }

        return userService.registerUser(user);
    }


    @GetMapping("{userName}")
    public ResponseEntity<UserDto> getUserByUserName(@PathVariable("userName") String userName){
        return userService.getUserByUserName(userName);
    }

    @GetMapping("corporate")
    public ResponseEntity<?> getCorporate(){
        return new ResponseEntity<>(HttpStatus.OK);
    }







}
