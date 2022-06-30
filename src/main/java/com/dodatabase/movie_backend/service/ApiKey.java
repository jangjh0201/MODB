package com.dodatabase.movie_backend.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "movie-api")
@ConstructorBinding
public class ApiKey {

    private final String Url;
    private final String Id;
    private final String Secret;

}
