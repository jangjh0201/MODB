package com.dodatabase.movie_backend.service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dodatabase.movie_backend.domain.Movie.MovieResponse;

@Service
@RequiredArgsConstructor
public class MovieApiService {

    private final WebClient movieApClient;

    public MovieResponse findByKeyword(String nation, String genre, String title) {
        Mono<MovieResponse> mono = movieApClient.mutate()
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("nation", nation)
                        .queryParam("genre", genre)
                        .queryParam("title", title)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MovieResponse.class);

        return mono.block();
    }

}