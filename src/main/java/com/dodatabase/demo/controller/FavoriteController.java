package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.repository.MovieCacheMemory;
import com.dodatabase.demo.service.FavoriteService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/favorites")
public class FavoriteController {

  private final FavoriteService favoriteService;
  private final MovieCacheMemory movieCacheMemory;
  private final ModelMapper modelMapper;

  @GetMapping("")
  public String list(Model model) {
    List<Movie> movies = favoriteService.findMovies();
    model.addAttribute("movies", movies);
    return "html/favorite/list";
  }

  @PostMapping("")
  @ResponseBody
  public ResponseEntity<Movie> createMovie(@RequestBody String id) {
    Optional<MovieResponse> cachedMovie;
    cachedMovie = Optional.ofNullable(movieCacheMemory.getMovieCacheById(id));

    if (cachedMovie.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    MovieResponse movieResponse = cachedMovie.get();
    if (favoriteService.findByTitle(movieResponse.getTitle()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    Movie movie = modelMapper.map(movieResponse, Movie.class);
    favoriteService.create(movie);
    return ResponseEntity.status(HttpStatus.CREATED).body(movie);
  }

  @DeleteMapping("")
  @ResponseBody
  public void removeMovie(@RequestBody String id) {
    favoriteService.deleteById(id);
  }
}
