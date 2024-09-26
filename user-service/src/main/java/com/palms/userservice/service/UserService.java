package com.palms.userservice.service;

import com.palms.userservice.dto.UserDto;
import com.palms.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.palms.userservice.model.User;

import java.util.Optional;


@Service
public class UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public ResponseEntity<String> registerUser(User user){

        user.setPassword(encoder.encode(user.getPassword()));
         userRepository.save(user);
       // authClient.loginUser(userDto);

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

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
