package com.dodatabase.movie_backend.config;

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
    private final String key;

    @Builder
    public ApiKey(String key) {
        this.key = key;
    }

}
