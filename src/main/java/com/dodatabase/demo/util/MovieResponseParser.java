package com.dodatabase.demo.util;

import com.dodatabase.demo.domain.movie.MovieData;
import com.dodatabase.demo.domain.movie.MovieResponse;
import java.io.IOException;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link MovieResponse} 객체를 {@link MovieData} 리스트로 변환해주는 클래스.
 */
public class MovieResponseParser {

  /**
   * {@link MovieResponse} 객체를 {@link MovieData} 리스트로 변환해주는 메서드.
   *
   * @param movieResponse {@link MovieResponse} 객체
   * @return {@link MovieData} 리스트
   * @throws IOException 예외 발생
   */
  public static List<MovieData> parse(MovieResponse movieResponse) {
    try {
      return movieResponse.getData().stream()
          .flatMap(data -> data.getResult().stream())
          .map(result -> MovieData.movieDataBuilder()
              .id(result.getDocId())
              .title(result.getTitle())
              .prodYear(Year.parse(result.getProdYear()))
              .genre(result.getGenre())
              .nation(result.getNation())
              .runtime(Integer.parseInt(result.getRuntime()))
              .directors(result.getDirectors().getDirectors().stream()
                  .map(director -> MovieData.Director.builder()
                      .directorNm(director.getDirectorNm())
                      .directorEnNm(director.getDirectorEnNm())
                      .directorId(director.getDirectorId())
                      .build())
                  .collect(Collectors.toList()))
              .actors(result.getActors().getActors().stream()
                  .map(actor -> MovieData.Actor.builder()
                      .actorNm(actor.getActorNm())
                      .actorEnNm(actor.getActorEnNm())
                      .actorId(actor.getActorId())
                      .build())
                  .collect(Collectors.toList()))
              .build())
          .collect(Collectors.toList());
    } catch (NullPointerException e) {
      // 데이터가 없거나 구조가 예상과 다를 경우 빈 리스트 반환
      return Collections.emptyList();
    }
  }
}
