package com.dodatabase.demo.service;

import com.dodatabase.demo.domain.movie.MovieRequest;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.util.JsonParser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieApiService {

  private final WebClient movieApiClient;

  public List<MovieResponse> findByKeyword(MovieRequest movieRequest) {
    String jsonResponse = getApiResponse(
        movieRequest.getNation(),
        movieRequest.getGenre(),
        movieRequest.getTitle()).block();

    return JsonParser.parseResponse(jsonResponse);
  }

  private Mono<String> getApiResponse(String nation, String genre, String title) {
    return movieApiClient.mutate()
        .build()
        .get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("nation", nation)
            .queryParam("genre", genre)
            .queryParam("title", title)
            .build())
        .accept(MediaType.TEXT_HTML)
        .retrieve()
        .bodyToMono(String.class);
  }

}
