package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.GenreType;
import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.domain.movie.NationType;
import com.dodatabase.demo.service.ExternalApiService;
import com.dodatabase.demo.service.WishListService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final ExternalApiService externalApiService;
  private final WishListService wishListService;

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/api/search")
  public String searchApiForm(Model model) {
    model.addAttribute("nations", NationType.values());
    model.addAttribute("genres", GenreType.values());
    return "api/html/apiSearchForm";
  }

  @PostMapping("/api/search")
  public String searchApi(
      @RequestParam(value = "nation", required = false) String nation,
      @RequestParam(value = "genre", required = false) String genre,
      @RequestParam(value = "title") String title,
      Model model) {
    List<MovieResponse> results = externalApiService.findByKeyword(nation, genre, title);

    model.addAttribute("movies", results);

    return "api/html/apiList";
  }

  @GetMapping("/movie")
  public String list(Model model) {
    List<Movie> movies = wishListService.findMovies();
    model.addAttribute("movies", movies);

    return "movie/html/movieList";
  }

}
