package com.dodatabase.movie_backend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
import com.dodatabase.movie_backend.domain.MovieWebClient.MovieApiWebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieApiService {

    // private final ApiKey apiKey;

    // private final RestTemplate restTemplate;

    private final MovieApiWebClient movieApiWebClient;

    // public MovieResponse findByKeyword(String keyword) {
    // final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
    // headers.set("X-NAVER-Client-ID", apiKey.getId());
    // headers.set("X-NAVER-Client-Secret", apiKey.getSecret());

    // Map<String, String> params = new HashMap<String, String>();
    // params.put("query", keyword);

    // final HttpEntity<String> entity = new HttpEntity<>(headers);

    // return restTemplate
    // .exchange(apiKey.getUrl(), HttpMethod.GET, entity, MovieResponse.class,
    // params)
    // .getBody();
    // }

    public Mono<MovieResponse> findByKeyword(String keyword) {

        return movieApiWebClient.findByKeyword(keyword);

    }

}