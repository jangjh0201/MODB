package com.dodatabase.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodatabase.backend.domain.movie.MovieRequest;
import com.dodatabase.backend.domain.movie.MovieResponse;
import java.io.IOException;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

public class MovieServiceTest {

  private static MockWebServer mockWebServer;

  private MovieService movieApiService;

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
    movieApiService = new MovieService(movieApiClient);
    movieRequest = MovieRequest.builder()
        .nation("미국")
        .genre("SF")
        .title("스타워즈")
        .build();

    jsonMovieResponse = """
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

  @AfterAll
  static void shutdown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  public void findByKewordTest() throws Exception {
    // given
    mockWebServer.enqueue(new MockResponse()
        .setBody(jsonMovieResponse)
        .addHeader("Content-Type", "text/html"));

    // when
    final List<MovieResponse> movieDataList = movieApiService.findByKeyword(movieRequest);

    // then
    assertNotNull(movieDataList);
    assertEquals("F10538", movieDataList.get(0).getId());
    assertEquals("스타워즈 에피소드 3 : 시스의 복수", movieDataList.get(0).getTitle());
    assertEquals(2005, movieDataList.get(0).getProdYear());
    assertEquals("액션,SF,어드벤처,판타지", movieDataList.get(0).getGenre());
    assertEquals("미국", movieDataList.get(0).getNation());
    assertEquals(139, movieDataList.get(0).getRuntime());
    assertEquals("조지 루카스", movieDataList.get(0).getDirector());
    assertEquals("이완 맥그리거", movieDataList.get(0).getActor());

    // Verify the request
    RecordedRequest recordedRequest = mockWebServer.takeRequest();
    // assertEquals("/?nation=미국&genre=SF&title=스타워즈", recordedRequest.getPath());
    assertEquals("GET", recordedRequest.getMethod());
  }
}
