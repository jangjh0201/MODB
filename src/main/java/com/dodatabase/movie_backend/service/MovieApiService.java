package com.dodatabase.movie_backend.service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class MovieApiService {

    private final WebClient movieApClient;

    public List<MovieResponse> findByKeyword(String nation, String genre, String title) {
        Mono<String> mono = movieApClient.mutate()
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("nation", nation)
                        .queryParam("genre", genre)
                        .queryParam("title", title)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        String jsonResponse = mono.block();
        return parseResponse(jsonResponse);
    }

    private List<MovieResponse> parseResponse(String jsonResponse) {
        List<MovieResponse> responseList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode dataArray = root.path("Data");

            for (JsonNode dataNode : dataArray) {
                JsonNode resultArray = dataNode.path("Result");

                for (JsonNode resultNode : resultArray) {
                    MovieResponse response = new MovieResponse();

                    // 제목
                    response.setTitle(resultNode.path("title").asText());
                    // 개봉
                    response.setProdYear(resultNode.path("prodYear").asInt());
                    // 장르
                    response.setGenre(resultNode.path("genre").asText());
                    // 제작국가
                    response.setNation(resultNode.path("nation").asText());
                    // 상영시간
                    response.setRuntime(resultNode.path("runtime").asInt());
                    // 감독
                    List<String> directorsList = new ArrayList<>();
                    JsonNode directorsArray = resultNode.path("directors").path("director");
                    for (JsonNode directorNode : directorsArray) {
                        directorsList.add(directorNode.path("directorNm").asText());
                    }
                    response.setDirectors(directorsList);
                    // 배우
                    List<String> actorsList = new ArrayList<>();
                    JsonNode actorsArray = resultNode.path("actors").path("actor");
                    for (JsonNode actorNode : actorsArray) {
                        actorsList.add(actorNode.path("actorNm").asText());
                    }
                    response.setActors(actorsList);

                    responseList.add(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseList;
    }
}