// package com.dodatabase.demo.repository;

// import static org.assertj.core.api.Assertions.assertThat;

// import com.dodatabase.demo.domain.movie.Movie;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// public class MovieCacheMemoryTest {

// private MovieCacheMemory movieCacheMemory;

// private Movie movie;
// private String id;

// @BeforeEach
// void initialize() {
// movieCacheMemory = new MovieCacheMemory();

// movie = Movie.builder("F10538")
// .title("스타워즈")
// .prodYear(1977)
// .genre("SF")
// .nation("미국")
// .runtime(121)
// .director("조지 루카스")
// .actor("이완 맥그리거")
// .build();

// id = "F10538";
// }

// @Test
// public void addMovieCacheTest() {

// movieCacheMemory.addMovieCache(movie);

// Movie cachedMovie = movieCacheMemory.getMovieCacheById(id);

// assertThat(cachedMovie).isNotNull();
// assertThat(cachedMovie.getId()).isEqualTo("F10538");
// assertThat(cachedMovie.getTitle()).isEqualTo("스타워즈");
// assertThat(cachedMovie.getProdYear()).isEqualTo(1977);
// assertThat(cachedMovie.getGenre()).isEqualTo("SF");
// assertThat(cachedMovie.getNation()).isEqualTo("미국");
// assertThat(cachedMovie.getRuntime()).isEqualTo(121);
// assertThat(cachedMovie.getDirector()).isEqualTo("조지 루카스");
// assertThat(cachedMovie.getActor()).isEqualTo("이완 맥그리거");
// }

// @Test
// public void clearCacheTest() {

// movieCacheMemory.addMovieCache(movie);
// movieCacheMemory.clearCache();

// Movie cachedMovie = movieCacheMemory.getMovieCacheById(id);
// assertThat(cachedMovie).isNull();
// }
// }
