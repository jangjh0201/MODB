package com.dodatabase.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodatabase.demo.domain.movie.Movie;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MovieRepositoryTest {

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private DataSource dataSource;

  private Movie movie;

  @BeforeEach
  void initialize() {
    movie = Movie.movieBuilder()
        .id("A00000")
        .title("스타워즈")
        .prodYear(1977)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("한 솔로")
        .build();
  }

  @Test
  public void dataSourceTest() {
    assertNotNull(dataSource);
  }

  @Test
  public void saveAndFindByIdTest() {

    movieRepository.save(movie);

    Optional<Movie> foundMovie = movieRepository.findById(movie.getId());
    assertThat(foundMovie).isPresent();
    assertThat(foundMovie.get().getTitle()).isEqualTo("스타워즈");
  }

  @Test
  public void findByTitleTest() {

    movieRepository.save(movie);

    Optional<Movie> foundMovie = movieRepository.findByTitle("스타워즈");
    assertThat(foundMovie).isPresent();
    assertThat(foundMovie.get().getTitle()).isEqualTo("스타워즈");
  }

  @Test
  public void deleteByIdTest() {

    movieRepository.save(movie);

    movieRepository.deleteById(movie.getId());

    Optional<Movie> deletedMovie = movieRepository.findById(movie.getId());
    assertThat(deletedMovie).isNotPresent();
  }
}
