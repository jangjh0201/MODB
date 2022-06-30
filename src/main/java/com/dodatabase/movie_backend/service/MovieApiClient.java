package com.dodatabase.movie_backend.service;

import com.dodatabase.movie_backend.domain.MovieDto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
// import org.springframework.web.util.UriComponentsBuilder;

// import java.net.URI;
// import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class MovieApiClient {

    ApiKey apiKey;

    private final RestTemplate restTemplate;

    private final String OpenNaverMovieUrl_getMovies = "https://openapi.naver.com/v1/search/movie.json?query={query}";

    public MovieDto requestMovie(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.set("X-NAVER-Client-ID", apiKey.getId());
        headers.set("X-NAVER-Client-Secret", apiKey.getSecret());

        Map<String, String> params = new HashMap<String, String>();
        params.put("query", keyword);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate
                .exchange(OpenNaverMovieUrl_getMovies, HttpMethod.GET, entity, MovieDto.class, params)
                .getBody();
    }

    // public MovieResponseDTO requestMovie2(String keyword) {
    // URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com")
    // .path("/v1/search/movie.json")
    // .queryParam("query",keyword)
    // .encode(Charset.forName("UTF-8"))
    // .build()
    // .toUri();
    // RestTemplate restTemplate = new RestTemplate();

    // RequestEntity<Void> requestEntity=
    // RequestEntity.get(uri)
    // .header("X-Naver-Client-ID",CLIENT_ID)
    // .header("X-Naver-Client-Secret",CLIENT_SECRET)
    // .build();
    // ResponseEntity<MovieResponseDTO> result =
    // restTemplate.exchange(requestEntity, MovieResponseDTO.class);
    // System.out.println(result.getBody());
    // return result.getBody();
    // }
}