package com.palms.userservice.repository;

import com.palms.userservice.dto.UserDto;
import com.palms.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);

    Optional<User> findByUserName(String userName);
}

