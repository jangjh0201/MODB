package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.MovieData;
import com.dodatabase.demo.domain.movie.MovieRequest;
import com.dodatabase.demo.service.MovieApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/movies")
public class MovieController {

  private final MovieApiService movieApiService;

  @GetMapping("")
  public String searchApi() {
    return "html/movie/list";
  }

  @PostMapping("")
  public String searchApi(
      @RequestBody MovieRequest movieRequest, Model model) {
    List<MovieData> movieDataList = movieApiService.findMovie(movieRequest);

    model.addAttribute("movies", movieDataList);

    return "html/movie/list";
  }
}
