package com.dodatabase.demo.service;

import com.dodatabase.demo.domain.movie.MovieData;
import com.dodatabase.demo.domain.movie.MovieRequest;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.util.MovieResponseParser;
import java.io.IOException;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

public class MovieApiServiceTest {

  private static MockWebServer mockWebServer;

  private MovieApiService movieApiService;

  private WebClient movieApiClient;

  private MovieRequest movieRequest;
  private String jsonMovieResponse;

  @BeforeAll
  static void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @BeforeEach
  void initialize() {
    String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
    movieApiClient = WebClient.create(baseUrl);
    movieApiService = new MovieApiService(movieApiClient);
    movieRequest = MovieRequest.builder()
        .nation("미국")
        .genre("SF")
        .title("스타워즈")
        .build();

    jsonMovieResponse = """
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

  @AfterAll
  static void shutdown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  public void findMovieTest() throws Exception {
    // given
    mockWebServer.enqueue(new MockResponse()
        .setBody(jsonMovieResponse)
        .addHeader("Content-Type", "application/json"));

    // when
    final List<MovieData> movieDataList = movieApiService.findMovie(movieRequest);

    // then
    assertNotNull(movieDataList);
    assertEquals("F10538", movieDataList.get(0).getId());
  }
}
