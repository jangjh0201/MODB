package com.dodatabase.movie_backend.service;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Builder;
import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "movie-api")
@ConstructorBinding
public class ApiKey {

    @NotBlank
    private final String id;

    @NotBlank
    private final String secret;

    @Builder
    public ApiKey(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

}
