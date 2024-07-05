package com.dodatabase.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dodatabase.demo.domain.wish.Wish;

@DataJpaTest
public class WishRepositoryTest {

  @Autowired
  private WishRepository movieRepository;

  private Wish wish;

  @BeforeEach
  void initialize() {
    wish = Wish.builder("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .posters(Arrays.asList("http://file.koreafilm.or.kr/thm/02/00/02/63/tn_DPF006410.JPG"))
        .plot("클론 전쟁이 시작되었던 때로부터 3년이 지나고...")
        .build();
  }

  @Test
  public void saveAndFindByIdTest() {

    movieRepository.save(wish);

    Optional<Wish> foundMovie = movieRepository.findById(wish.getId());
    assertThat(foundMovie).isPresent();
    assertThat(foundMovie.get().getTitle()).isEqualTo("스타워즈 에피소드 3 : 시스의 복수");
  }

  @Test
  public void findByTitleTest() {

    movieRepository.save(wish);

    Optional<Wish> foundMovie = movieRepository.findByTitle("스타워즈 에피소드 3 : 시스의 복수");
    assertThat(foundMovie).isPresent();
    assertThat(foundMovie.get().getTitle()).isEqualTo("스타워즈 에피소드 3 : 시스의 복수");
  }

  @Test
  public void deleteByIdTest() {

    movieRepository.save(wish);

    movieRepository.deleteById(wish.getId());

    Optional<Wish> deletedMovie = movieRepository.findById(wish.getId());
    assertThat(deletedMovie).isNotPresent();
  }
}
