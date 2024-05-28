package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.service.MovieCacheService;
import com.dodatabase.demo.service.WishListService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class WishListController {

  private final WishListService wishListService;
  private final MovieCacheService movieCacheService;
  private final ModelMapper modelMapper;

  @GetMapping("/movie")
  public String list(Model model) {
    List<Movie> movies = wishListService.findMovies();
    model.addAttribute("movies", movies);

    return "movie/html/movieList";
  }

  @PostMapping("/movie/new")
  @ResponseBody
  public ResponseEntity<Movie> addMovie(@RequestBody Long movieId) {
    Optional<MovieResponse> movieResponseOptional = Optional.ofNullable(movieCacheService.getMovieById(movieId));
    if (movieResponseOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    MovieResponse movieResponse = movieResponseOptional.get();
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
