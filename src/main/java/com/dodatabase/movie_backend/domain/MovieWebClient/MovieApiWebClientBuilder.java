package com.dodatabase.movie_backend.domain.MovieWebClient;

import org.springframework.web.reactive.function.client.WebClient;

public class MovieApiWebClientBuilder {
    private static final String HOST = "https://openapi.naver.com";

    public static WebClient get() {
        return WebClient
                .builder()
                .baseUrl(HOST)
                .build();
    }
}
