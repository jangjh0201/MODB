package com.dodatabase.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.repository.MovieCacheMemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieCacheMemoryTest {

  private MovieCacheMemory movieCacheMemory;

  @BeforeEach
  void setUp() {
    movieCacheMemory = new MovieCacheMemory();
  }

  @Test
  public void testAddAndGetMovie() {
    MovieResponse movieResponse = MovieResponse.builder()
        .title("스타워즈")
        .prodYear(1977)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("한 솔로")
        .build();

    long id = movieCacheMemory.addMovie(movieResponse);
    MovieResponse cachedMovie = movieCacheMemory.getMovieById(id);

    assertThat(cachedMovie).isNotNull();
    assertThat(cachedMovie.getTitle()).isEqualTo("스타워즈");
  }

  @Test
  public void testClearCache() {
    MovieResponse movieResponse = MovieResponse.builder()
        .title("스타워즈")
        .prodYear(1977)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("한 솔로")
        .build();

    movieCacheMemory.addMovie(movieResponse);
    movieCacheMemory.clearCache();

    assertThat(movieCacheMemory.getMovieById(0L)).isNull();
  }
}
