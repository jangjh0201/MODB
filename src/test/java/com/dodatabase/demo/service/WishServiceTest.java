package com.dodatabase.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodatabase.demo.domain.wish.Wish;
import com.dodatabase.demo.domain.wish.WishRequest;
import com.dodatabase.demo.domain.wish.WishResponse;
import com.dodatabase.demo.repository.WishRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WishServiceTest {

  @Mock
  private WishRepository movieRepository;

  @InjectMocks
  private WishService wishService;

  private Wish wish;
  private WishRequest wishRequest;

  @BeforeEach
  void initialize() {
    MockitoAnnotations.openMocks(this);
    wish = Wish.builder("F10538")
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
    wishService.create(wishRequest);
    verify(movieRepository, times(1)).save(wish);
  }

  @Test
  void findMoviesTest() {
    when(movieRepository.findAll()).thenReturn(Arrays.asList(wish));
    List<WishResponse> wishResponses = wishService.findWishes();
    assertEquals(1, wishResponses.size());
    assertEquals(wish.getTitle(), wishResponses.get(0).getTitle());
  }

  @Test
  void findByIdTest() {
    when(movieRepository.findById("F10538")).thenReturn(Optional.of(wish));
    Optional<WishResponse> foundMovie = wishService.findById("F10538");
    assertTrue(foundMovie.isPresent());
    assertEquals(wish.getTitle(), foundMovie.get().getTitle());
  }

  @Test
  void deleteByIdTest() {
    wishService.deleteById("F10538");
    verify(movieRepository, times(1)).deleteById("F10538");
  }
}
