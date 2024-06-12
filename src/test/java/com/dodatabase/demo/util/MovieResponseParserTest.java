package com.dodatabase.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.movie.MovieData;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Year;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieResponseParserTest {

  String result;

  @BeforeEach
  void initialize() {
    result = """
        {
          "data": [
            {
              "result": [
                {
                  "DOCID": "F10538",
                  "title": "스타워즈 에피소드 3 : 시스의 복수",
                  "prodYear": "2005",
                  "genre": "액션,SF,어드벤처,판타지",
                  "nation": "미국",
                  "runtime": "139",
                  "directors": {
                    "director": [
                      {
                        "directorNm": "조지 루카스",
                        "directorEnNm": "George Lucas",
                        "directorId": "00049192"
                      }
                    ]
                  },
                  "actors": {
                    "actor": [
                      {
                        "actorNm": "이완 맥그리거",
                        "actorEnNm": "Ewan McGregor",
                        "actorId": "00049011"
                      }
                    ]
                  }
                }
              ]
            }
          ]
        }
        """;
  }

  @Test
  public void parseTest() throws Exception {
    // JSON 문자열을 MovieResponse 객체로 변환
    ObjectMapper objectMapper = new ObjectMapper();
    MovieResponse movieResponse = objectMapper.readValue(result, MovieResponse.class);

    // MovieResponse 객체를 MovieData 리스트로 변환
    List<MovieData> movieDataList = MovieResponseParser.parse(movieResponse);

    // then
    assertNotNull(movieDataList);
    assertEquals(1, movieDataList.size());
    MovieData movieData = movieDataList.get(0);
    assertEquals("F10538", movieData.getId());
    assertEquals("스타워즈 에피소드 3 : 시스의 복수", movieData.getTitle());
    assertEquals(Year.of(2005), movieData.getProdYear());
    assertEquals("액션,SF,어드벤처,판타지", movieData.getGenre());
    assertEquals("미국", movieData.getNation());
    assertEquals(139, movieData.getRuntime());
    assertEquals("조지 루카스", movieData.getDirectorNames());
    assertEquals("이완 맥그리거", movieData.getActorNames());
  }
}
