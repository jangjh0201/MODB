package com.dodatabase.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.dodatabase.demo.domain.movie.Movie;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MovieRepositoryTest {

  @Autowired
  private MovieRepository movieRepository;

  private Movie movie;

  @BeforeEach
  void initialize() {
    movie = Movie.builder("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .build();
  }

  @Test
  public void saveAndFindByIdTest() {

    movieRepository.save(movie);

    Optional<Movie> foundMovie = movieRepository.findById(movie.getId());
    assertThat(foundMovie).isPresent();
    assertThat(foundMovie.get().getTitle()).isEqualTo("스타워즈 에피소드 3 : 시스의 복수");
  }

  @Test
  public void findByTitleTest() {

    movieRepository.save(movie);

    Optional<Movie> foundMovie = movieRepository.findByTitle("스타워즈 에피소드 3 : 시스의 복수");
    assertThat(foundMovie).isPresent();
    assertThat(foundMovie.get().getTitle()).isEqualTo("스타워즈 에피소드 3 : 시스의 복수");
  }

  @Test
  public void deleteByIdTest() {

    movieRepository.save(movie);

    movieRepository.deleteById(movie.getId());

    Optional<Movie> deletedMovie = movieRepository.findById(movie.getId());
    assertThat(deletedMovie).isNotPresent();
  }
}
