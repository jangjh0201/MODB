package com.dodatabase.movie_backend.domain.MovieWebClient;

import org.springframework.web.reactive.function.client.WebClient;

import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
import com.dodatabase.movie_backend.service.ApiKey;

import lombok.Builder;
import reactor.core.publisher.Mono;

public class MovieApiWebClient {
    private static final String MOVIE_V1_FIND_BY_KEYWORD_URL = "/v1/search/movie.json";

    private final ApiKey apiKey;

    private final WebClient webClient;

    @Builder
    public MovieApiWebClient(WebClient webClient, ApiKey apiKey) {
        this.webClient = webClient;
        this.apiKey = apiKey;
    }

    public Mono<MovieResponse> findByKeyword(String keyword) {
        return webClient.mutate()
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path(MOVIE_V1_FIND_BY_KEYWORD_URL)
                        .queryParam("query", keyword)
                        .build())
                .headers(httpHeaders -> {
                    httpHeaders.add("X-NAVER-Client-ID", apiKey.getId());
                    httpHeaders.add("X-NAVER-Client-Secret", apiKey.getSecret());
                })
                .retrieve()
                .bodyToMono(MovieResponse.class);
    }
}
