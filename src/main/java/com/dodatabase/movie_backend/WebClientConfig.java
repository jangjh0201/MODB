package com.dodatabase.movie_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dodatabase.movie_backend.domain.MovieWebClient.MovieApiWebClient;
import com.dodatabase.movie_backend.domain.MovieWebClient.MovieApiWebClientBuilder;
import com.dodatabase.movie_backend.service.ApiKey;

import lombok.Builder;

@Configuration
public class WebClientConfig {

    private final ApiKey apiKey;

    @Builder
    public WebClientConfig(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    @Bean
    public MovieApiWebClient movieApiWebClient() {
        return new MovieApiWebClient(MovieApiWebClientBuilder.get(), apiKey);
    }
}
