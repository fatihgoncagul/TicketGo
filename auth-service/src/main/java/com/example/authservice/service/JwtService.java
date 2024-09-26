package com.example.authservice.service;

import com.example.authservice.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;



    public String generateToken(UserDto userDto) {

        Map<String , Object> claims = new HashMap<>();

        claims.put("roles", userDto.getRoles().stream()
                .map(role -> "ROLE_" + role.name())
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)

                .setSubject(userDto.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*400))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();

    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
