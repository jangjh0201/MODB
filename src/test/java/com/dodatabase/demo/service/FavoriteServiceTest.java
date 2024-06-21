package com.dodatabase.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.wishlist.WishRequest;
import com.dodatabase.demo.domain.wishlist.WishResponse;
import com.dodatabase.demo.repository.MovieRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FavoriteServiceTest {

  @Mock
  private MovieRepository movieRepository;

  @InjectMocks
  private FavoriteService favoriteService;

  private Movie movie;
  private WishRequest wishRequest;

  @BeforeEach
  void initialize() {
    MockitoAnnotations.openMocks(this);
    movie = Movie.builder("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .build();

    wishRequest = WishRequest.builder()
        .id("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .build();
  }

  @Test
  void createMovieTest() {
    favoriteService.create(wishRequest);
    verify(movieRepository, times(1)).save(movie);
  }

  @Test
  void findMoviesTest() {
    when(movieRepository.findAll()).thenReturn(Arrays.asList(movie));
    List<WishResponse> movies = favoriteService.findMovies();
    assertEquals(1, movies.size());
    assertEquals(movie.getTitle(), movies.get(0).getTitle());
  }

  @Test
  void findByIdTest() {
    when(movieRepository.findById("F10538")).thenReturn(Optional.of(movie));
    Optional<WishResponse> foundMovie = favoriteService.findById("F10538");
    assertTrue(foundMovie.isPresent());
    assertEquals(movie.getTitle(), foundMovie.get().getTitle());
  }

  @Test
  void deleteByIdTest() {
    favoriteService.deleteById("F10538");
    verify(movieRepository, times(1)).deleteById("F10538");
  }
}
