// package com.dodatabase.movie_backend.service;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.web.reactive.function.client.WebClient;

// import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
// import com.dodatabase.movie_backend.domain.Movie.MovieResponseItem;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import okhttp3.mockwebserver.MockResponse;
// import okhttp3.mockwebserver.MockWebServer;
// import reactor.core.publisher.Mono;
// import reactor.test.StepVerifier;

// import java.io.IOException;
// import java.util.LinkedList;
// import java.util.List;

// public class MovieApiServiceTest {

// private static MockWebServer mockWebServer;

// private MovieApiService movieApiService;

// private WebClient movieApiClient;

// private ObjectMapper objectMapper = new ObjectMapper();

// @BeforeAll
// static void setUp() throws IOException {
// mockWebServer = new MockWebServer();
// mockWebServer.start();
// }

// @BeforeEach
// void initialize() {
// String baseUrl = String.format("http://localhost:%s",
// mockWebServer.getPort());
// movieApiClient = WebClient.create(baseUrl);
// movieApiService = new MovieApiService(movieApiClient);
// }

// @AfterAll
// static void shutdown() throws IOException {
// mockWebServer.shutdown();
// }

// @Test
// public void findByKeyword() throws Exception {
// // given
// List<MovieResponseItem> items = new LinkedList<>();
// MovieResponse movieResponse = new MovieResponse(null, 0, items);
// mockWebServer.enqueue(new MockResponse()
// .setBody(objectMapper.writeValueAsString(movieResponse))
// .addHeader("Content-Type", "application/json"));

// // when
// final MovieResponse result = movieApiService.findByKeyword("JP", "15",
// "star");

// // then
// StepVerifier.create(Mono.just(result))
// .expectNextMatches(data -> data.getClass().equals(movieResponse.getClass()))
// .verifyComplete();
// }

// }
