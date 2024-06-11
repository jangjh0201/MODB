package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.repository.MovieCacheMemory;
import com.dodatabase.demo.service.MovieApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/movies")
public class MovieController {

  private final MovieApiService movieApiService;
  private final MovieCacheMemory movieCacheMemory;

  @GetMapping("")
  public String searchApi() {
    return "html/movie/list";
  }

  @PostMapping("")
  public String searchApi(
      @RequestParam(value = "nation", required = false) String nation,
      @RequestParam(value = "genre", required = false) String genre,
      @RequestParam(value = "title") String title,
      Model model) {
    List<Movie> results = movieApiService.findByKeyword(nation, genre, title);

    model.addAttribute("movies", results);

    return "html/movie/list";
  }
}
