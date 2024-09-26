package com.palms.userservice.dto;

import com.palms.userservice.model.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private String userName;
    private String password;
    private List<Role> roles;

}
