package com.dodatabase.demo.util;

import com.dodatabase.demo.domain.movie.MovieDetail;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * JSON 형태의 응답을 받아 {@link MovieResponse} 타입의 List를 반환하는 클래스.
 */
public class JsonParser {

  /**
   * JSON 형태의 문자열을 {@link MovieResponse} 타입의 객체로 변환해주는 메소드.
   *
   * @param jsonResponse JSON 형식의 문자열, 이 문자열은 영화 데이터를 포함해야 합니다.
   * @return 변환된 {@link MovieResponse} 타입의 리스트, JSON 파싱에 실패하면 빈 리스트를 반환합니다.
   * @throws IOException JSON 파싱 중 발생할 수 있는 예외.
   */
  public static List<MovieResponse> parseResponse(String jsonResponse) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode root = objectMapper.readTree(jsonResponse);
      JsonNode dataArray = root.path("Data");

      return StreamSupport.stream(dataArray.spliterator(), false)
          .flatMap(dataNode -> StreamSupport.stream(dataNode.path("Result").spliterator(), false))
          .map(JsonParser::parseResultNode)
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  /**
   * JSON 노드를 {@link MovieResponse} 객체로 변환하는 메소드.
   *
   * @param resultNode 변환할 JSON 노드
   * @return 변환된 {@link MovieResponse} 객체
   */
  private static MovieResponse parseResultNode(JsonNode resultNode) {
    String id = resultNode.path("DOCID").asText();
    String title = resultNode.path("title").asText().replaceAll("!HS\\s|!HE\\s", "");
    int prodYear = resultNode.path("prodYear").asInt();
    String genre = resultNode.path("genre").asText();
    String nation = resultNode.path("nation").asText();
    int runtime = resultNode.path("runtime").asInt();
    String directors = parseDirectors(resultNode.path("directors").path("director"));
    String actors = parseActors(resultNode.path("actors").path("actor"));
    List<String> posters = parsePosters(resultNode.path("posters"));
    String plots = parsePlots(resultNode.path("plots").path("plot"));

    MovieDetail movieDetail = MovieDetail.builder()
        .posters(posters)
        .plot(plots)
        .build();

    return MovieResponse.builder()
        .id(id)
        .title(title)
        .prodYear(prodYear)
        .genre(genre)
        .nation(nation)
        .runtime(runtime)
        .director(directors)
        .actor(actors)
        .movieDetail(movieDetail)
        .build();
  }

  private static String parseDirectors(JsonNode directorsNode) {
    return StreamSupport.stream(directorsNode.spliterator(), false)
        .map(directorNode -> directorNode.path("directorNm").asText())
        .collect(Collectors.joining(", "));
  }

  private static String parseActors(JsonNode actorsNode) {
    return StreamSupport.stream(actorsNode.spliterator(), false)
        .map(actorNode -> actorNode.path("actorNm").asText())
        .collect(Collectors.joining(", "));
  }

  private static List<String> parsePosters(JsonNode postersNode) {
    return Arrays.asList(postersNode.asText().split("\\|"));
  }

  private static String parsePlots(JsonNode plotsNode) {
    return StreamSupport.stream(plotsNode.spliterator(), false)
        .filter(plotNode -> "한국어".equals(plotNode.path("plotLang").asText()))
        .map(plotNode -> plotNode.path("plotText").asText())
        .collect(Collectors.joining("\n")).replaceAll("'", "");
  }
}
