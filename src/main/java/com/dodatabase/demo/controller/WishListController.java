package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.repository.MovieCacheMemory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/movie")
public class WishListController {

  private final WishListService wishListService;
  private final MovieCacheMemory movieCacheMemory;
  private final ModelMapper modelMapper;

  @GetMapping("")
  public String list(Model model) {
    List<Movie> movies = wishListService.findMovies();
    model.addAttribute("movies", movies);
    return "html/movie/list";
  }

  @PostMapping("/create")
  @ResponseBody
  public ResponseEntity<Movie> createMovie(@RequestBody Long movieId) {
    Optional<MovieResponse> cache = Optional.ofNullable(movieCacheMemory.getMovieById(movieId));

    if (cache.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    MovieResponse movieResponse = cache.get();
    if (wishListService.findByTitle(movieResponse.getTitle()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    Movie movie = modelMapper.map(movieResponse, Movie.class);
    wishListService.create(movie);
    return ResponseEntity.status(HttpStatus.CREATED).body(movie);
  }

  @PostMapping("/delete")
  @ResponseBody
  public void removeMovie(@RequestBody Long movieId) {
    wishListService.deleteById(movieId);
  }
}
