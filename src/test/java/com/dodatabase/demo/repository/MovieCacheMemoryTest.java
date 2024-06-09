package com.dodatabase.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.dodatabase.demo.domain.movie.MovieResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieCacheMemoryTest {

  private MovieCacheMemory movieCacheMemory;

  private MovieResponse movieResponse;
  private String id;

  @BeforeEach
  void initialize() {
    movieCacheMemory = new MovieCacheMemory();

    movieResponse = MovieResponse.movieResponseBuilder()
        .id("A00000")
        .title("스타워즈")
        .prodYear(1977)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("한 솔로")
        .build();

    id = "A00000";
  }

  @Test
  public void addMovieCacheTest() {

    movieCacheMemory.addMovieCache(movieResponse);

    MovieResponse cachedMovie = movieCacheMemory.getMovieCacheById(id);

    assertThat(cachedMovie).isNotNull();
    assertThat(cachedMovie.getId()).isEqualTo("A00000");
    assertThat(cachedMovie.getTitle()).isEqualTo("스타워즈");
    assertThat(cachedMovie.getProdYear()).isEqualTo(1977);
    assertThat(cachedMovie.getGenre()).isEqualTo("SF");
    assertThat(cachedMovie.getNation()).isEqualTo("미국");
    assertThat(cachedMovie.getRuntime()).isEqualTo(121);
    assertThat(cachedMovie.getDirector()).isEqualTo("조지 루카스");
    assertThat(cachedMovie.getActor()).isEqualTo("한 솔로");
  }

  @Test
  public void clearCacheTest() {

    movieCacheMemory.addMovieCache(movieResponse);
    movieCacheMemory.clearCache();

    MovieResponse cachedMovie = movieCacheMemory.getMovieCacheById(id);
    assertThat(cachedMovie).isNull();
  }
}
