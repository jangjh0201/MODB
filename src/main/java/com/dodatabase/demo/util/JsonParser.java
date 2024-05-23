package com.dodatabase.demo.util;

import com.dodatabase.demo.domain.movie.MovieResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JsonParser {

  public static List<MovieResponse> parseResponse(String jsonResponse) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode root = objectMapper.readTree(jsonResponse);
      JsonNode dataArray = root.path("Data");

      return StreamSupport.stream(dataArray.spliterator(), false)
          .flatMap(dataNode -> StreamSupport.stream(dataNode.path("Result").spliterator(), false))
          .map(resultNode -> {

            String title = resultNode.path("title").asText().replaceAll("!HS\\s|!HE\\s", "");
            int prodYear = resultNode.path("prodYear").asInt();
            String genre = resultNode.path("genre").asText();
            String nation = resultNode.path("nation").asText();
            int runtime = resultNode.path("runtime").asInt();
            String directors = StreamSupport
                .stream(resultNode.path("directors").path("director").spliterator(), false)
                .map(directorNode -> directorNode.path("directorNm").asText())
                .collect(Collectors.joining(", "));
            String actors = StreamSupport
                .stream(resultNode.path("actors").path("actor").spliterator(), false)
                .map(actorNode -> actorNode.path("actorNm").asText())
                .collect(Collectors.joining(", "));

            MovieResponse movieResponse = MovieResponse.builder()
                .title(title)
                .prodYear(prodYear)
                .genre(genre)
                .nation(nation)
                .runtime(runtime)
                .director(directors)
                .actor(actors)
                .build();

            return movieResponse;
          })
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}
