package com.example.authservice.dto;

import com.example.authservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userName;
    private String password;
    private List<Role> roles;
}
