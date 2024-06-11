package com.dodatabase.demo.service;

import com.dodatabase.demo.domain.movie.Movie;
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
  private String result;

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

  @AfterAll
  static void shutdown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  public void findByKeywordTest() throws Exception {
    // given
    mockWebServer.enqueue(new MockResponse()
        .setBody(result)
        .addHeader("Content-Type", "application/json"));

    // when
    final List<Movie> Movies = movieApiService.findByKeyword("미국", "SF", "스타워즈");

    // then
    StepVerifier.create(Mono.just(Movies))
        .expectNextMatches(data -> data.size() == 1 && data.get(0).getTitle().equals("스타워즈"))
        .verifyComplete();
  }
}
