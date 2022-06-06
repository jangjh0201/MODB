package com.dodatabase.movie_backend.service;

import com.dodatabase.movie_backend.domain.MovieResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieApiClient {

    private final RestTemplate restTemplate;

    private final String CLIENT_ID = "Hqsz1tECcg712EE903wl";
    private final String CLIENT_SECRET = "av_jqTlPjr";

    private final String OpenNaverMovieUrl_getMovies = "https://openapi.naver.com/v1/search/movie.json?query={query}";

    public MovieResponseDTO requestMovie(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.set("X-NAVER-Client-ID", CLIENT_ID);
        headers.set("X-NAVER-Client-Secret", CLIENT_SECRET);

        Map<String, String> params = new HashMap<String, String>();
        params.put("query", keyword);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate
                .exchange(OpenNaverMovieUrl_getMovies, HttpMethod.GET, entity, MovieResponseDTO.class, params)
                .getBody();
    }

}