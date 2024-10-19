package com.palms.userservice.service;

import com.palms.userservice.dto.LogDto;
import com.palms.userservice.dto.UserDto;
import com.palms.userservice.exception.UserNotFoundException;
import com.palms.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.palms.userservice.model.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Service
public class UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private final UserRepository userRepository;

    private final KafkaTemplate<String, LogDto> kafkaTemplate;


    public UserService(UserRepository userRepository, KafkaTemplate<String, LogDto> kafkaTemplate) {
        this.userRepository = userRepository;

        this.kafkaTemplate = kafkaTemplate;
    }


    public ResponseEntity<String> registerUser(User user){

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        //authClient.loginUser(userDto);

        return new ResponseEntity<>("Success!",HttpStatus.OK);
    }


    public ResponseEntity<UserDto> getUserByUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);

        if (user.isPresent()) {
            UserDto userDto = mapToUserDto(user.get());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Optional<User> findUserByUserName(String username) {

        if (!userRepository.findByUserName(username).isPresent()){
             throw new UserNotFoundException("User with "+username+" not found");
        }
        return userRepository.findByUserName(username);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());

        return userDto;
    }


    public String sendLog(@RequestBody LogDto logEntry){

        kafkaTemplate.send("logs",logEntry);


        return "Log:  '"+ logEntry +"' is sent to Kafka";
    }
}
