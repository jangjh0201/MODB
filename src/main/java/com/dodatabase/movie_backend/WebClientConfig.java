package com.dodatabase.movie_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.dodatabase.movie_backend.service.ApiKey;

import lombok.Builder;

@Configuration
public class WebClientConfig {

    private static final String NAVER_HOST = "https://openapi.naver.com/v1/search/movie.json";

    private final ApiKey apiKey;

    @Builder
    public WebClientConfig(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    @Bean
    public WebClient movieApiClient() {
        return WebClient.builder()
                .baseUrl(NAVER_HOST)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("X-NAVER-Client-ID", apiKey.getId());
                    httpHeaders.add("X-NAVER-Client-Secret", apiKey.getSecret());
                })
                .build();
    }
}
