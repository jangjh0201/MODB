package com.dodatabase.movie_backend.service;

import com.dodatabase.movie_backend.domain.MovieDto;

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

    public MovieDto requestMovie(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.set("X-NAVER-Client-ID", apiKey.getId());
        headers.set("X-NAVER-Client-Secret", apiKey.getSecret());

        Map<String, String> params = new HashMap<String, String>();
        params.put("query", keyword);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate
                .exchange(apiKey.getUrl(), HttpMethod.GET, entity, MovieDto.class, params)
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