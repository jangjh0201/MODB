package com.dodatabase.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.dodatabase.demo.domain.movie.MovieResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieCacheMemoryTest {

  private MovieCacheMemory movieCacheMemory;

  @BeforeEach
  public void setUp() {
    movieCacheMemory = new MovieCacheMemory();
  }

  @Test
  public void testAddAndGetMovie() {
    MovieResponse movieResponse = MovieResponse.movieResponseBuilder()
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
    assertThat(cachedMovie.getProdYear()).isEqualTo(1977);
    assertThat(cachedMovie.getGenre()).isEqualTo("SF");
    assertThat(cachedMovie.getNation()).isEqualTo("미국");
    assertThat(cachedMovie.getRuntime()).isEqualTo(121);
    assertThat(cachedMovie.getDirector()).isEqualTo("조지 루카스");
    assertThat(cachedMovie.getActor()).isEqualTo("한 솔로");
  }

  @Test
  public void testClearCache() {
    MovieResponse movieResponse = MovieResponse.movieResponseBuilder()
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

    MovieResponse cachedMovie = movieCacheMemory.getMovieById(0);
    assertThat(cachedMovie).isNull();
  }
}
