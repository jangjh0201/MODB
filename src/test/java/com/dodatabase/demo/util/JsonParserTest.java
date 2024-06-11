package com.dodatabase.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodatabase.demo.domain.movie.Movie;
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
                "DOCID": "A00000",
                "title": "스타워즈",
                "prodYear": 1977,
                "genre": "SF",
                "nation": "미국",
                "runtime": 121,
                "directors": {
                  "director": [
                    { "directorNm": "조지 루카스" }
                  ]
                },
                "actors": {
                  "actor": [
                    { "actorNm": "한 솔로" }
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
    List<Movie> Movies = JsonParser.parseResponse(result);

    // then
    assertNotNull(Movies);
    assertEquals(1, Movies.size());
    Movie Movie = Movies.get(0);
    assertEquals("A00000", Movie.getId());
    assertEquals("스타워즈", Movie.getTitle());
    assertEquals(1977, Movie.getProdYear());
    assertEquals("SF", Movie.getGenre());
    assertEquals("미국", Movie.getNation());
    assertEquals(121, Movie.getRuntime());
    assertEquals("조지 루카스", Movie.getDirector());
    assertEquals("한 솔로", Movie.getActor());
  }
}
