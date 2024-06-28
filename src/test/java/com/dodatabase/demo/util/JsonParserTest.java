package com.dodatabase.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodatabase.demo.domain.movie.MovieResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonParserTest {

  String result;

  @BeforeEach
  void initialize() {
    result = """
        {
          "Data": [
            {
              "Result": [
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
  public void parseResponseTest() {

    // when
    List<MovieResponse> movieResponses = JsonParser.parseResponse(result);

    // then
    assertNotNull(movieResponses);
    assertEquals(1, movieResponses.size());
    MovieResponse movieResponse = movieResponses.get(0);
    assertEquals("F10538", movieResponse.getId());
    assertEquals("스타워즈 에피소드 3 : 시스의 복수", movieResponse.getTitle());
    assertEquals(2005, movieResponse.getProdYear());
    assertEquals("액션,SF,어드벤처,판타지", movieResponse.getGenre());
    assertEquals("미국", movieResponse.getNation());
    assertEquals(139, movieResponse.getRuntime());
    assertEquals("조지 루카스", movieResponse.getDirector());
    assertEquals("이완 맥그리거", movieResponse.getActor());
  }
}
