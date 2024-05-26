package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.service.WishListService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JpaController {

  private final WishListService wishListService;

  private final ModelMapper modelMapper;

  @PostMapping("/movie/new")
  public ResponseEntity<Movie> addMovie(@RequestBody MovieResponse movieResponse) {
    Optional<Movie> existingMovie = wishListService.findByTitle(movieResponse.getTitle());

    try {
      if (existingMovie.isPresent()) {
        throw new Exception("이미 존재하는 영화입니다.");
      } else {
        wishListService.create(modelMapper.map(movieResponse, Movie.class));
        return ResponseEntity.status(HttpStatus.CREATED).build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PostMapping("/movie/delete")
  public void removeMovies(@RequestBody MovieResponse movieResponse) {
    wishListService.deleteByTitle(movieResponse.getTitle());
  }
}
