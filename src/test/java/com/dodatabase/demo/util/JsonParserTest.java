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
                  },
                  "plots": {
                    "plot": [
                      {
                        "plotLang": "한국어",
                        "plotText": "클론 전쟁이 시작되었던 때로부터 3년이 지나고..."
                      },
                      {
                        "plotLang": "영어",
                        "plotText": "It has been three years since the Clone Wars began..."
                      }
                    ]
                  },
                  "posters": "http://file.koreafilm.or.kr/thm/02/00/02/63/tn_DPF006410.JPG"
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
    assertEquals("클론 전쟁이 시작되었던 때로부터 3년이 지나고...", movieResponse.getMovieDetail().getPlot());
    assertEquals("http://file.koreafilm.or.kr/thm/02/00/02/63/tn_DPF006410.JPG",
        movieResponse.getMovieDetail().getPosters().get(0));
  }

}
