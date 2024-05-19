package com.dodatabase.movie_backend.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    @SuppressWarnings("unused")
    public static List<MovieResponse> parseResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode dataArray = root.path("Data");

            return StreamSupport.stream(dataArray.spliterator(), false)
                    .flatMap(dataNode -> StreamSupport.stream(dataNode.path("Result").spliterator(), false))
                    .map(resultNode -> {
                        MovieResponse response = new MovieResponse();

                        // Title 처리
                        String title = resultNode.path("title").asText().replaceAll("!HS\\s|!HE\\s", "");
                        response.setTitle(title);

                        response.setProdYear(resultNode.path("prodYear").asInt());
                        response.setGenre(resultNode.path("genre").asText());
                        response.setNation(resultNode.path("nation").asText());
                        response.setRuntime(resultNode.path("runtime").asInt());

                        // 감독 정보 설정
                        String directors = StreamSupport
                                .stream(resultNode.path("directors").path("director").spliterator(), false)
                                .map(directorNode -> directorNode.path("directorNm").asText())
                                .collect(Collectors.joining(", "));
                        response.setDirector(directors);

                        // 배우 정보 설정
                        String actors = StreamSupport
                                .stream(resultNode.path("actors").path("actor").spliterator(), false)
                                .map(actorNode -> actorNode.path("actorNm").asText())
                                .collect(Collectors.joining(", "));
                        response.setActor(actors);

                        return response;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}