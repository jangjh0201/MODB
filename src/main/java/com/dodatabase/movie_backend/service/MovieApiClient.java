package com.dodatabase.movie_backend.service;

import com.dodatabase.movie_backend.domain.MovieResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieApiClient {

    private final ApiKey apiKey;

    private final RestTemplate restTemplate;

    public MovieResponse requestMovie(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.set("X-NAVER-Client-ID", apiKey.getId());
        headers.set("X-NAVER-Client-Secret", apiKey.getSecret());

        Map<String, String> params = new HashMap<String, String>();
        params.put("query", keyword);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate
                .exchange(apiKey.getUrl(), HttpMethod.GET, entity, MovieResponse.class, params)
                .getBody();
    }

}