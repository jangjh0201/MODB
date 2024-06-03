package com.dodatabase.demo.service;

import com.dodatabase.demo.domain.movie.MovieResponse;
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
import reactor.test.StepVerifier;

public class MovieApiServiceTest {

  private static MockWebServer mockWebServer;

  private MovieApiService movieApiService;

  private WebClient movieApiClient;

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
  }

  @AfterAll
  static void shutdown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  public void findByKeyword() throws Exception {
    // given
    String jsonResponse = """
        {
        "Data": [
          {
            "Result": [
              {
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
    mockWebServer.enqueue(new MockResponse()
        .setBody(jsonResponse)
        .addHeader("Content-Type", "application/json"));

    // when
    final List<MovieResponse> result = movieApiService.findByKeyword("미국", "SF", "스타워즈");

    // then
    StepVerifier.create(Mono.just(result))
        .expectNextMatches(data -> data.size() == 1 && data.get(0).getTitle().equals("스타워즈"))
        .verifyComplete();
  }
}
