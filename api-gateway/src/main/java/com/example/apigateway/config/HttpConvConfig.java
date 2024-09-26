package com.example.apigateway.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConvConfig {

    @Bean
    public HttpMessageConverters customConverters() {
        return new HttpMessageConverters();
    }
}