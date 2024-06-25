package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.MovieRequest;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.service.MovieService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class MovieController {

  private final MovieService movieApiService;

  @GetMapping("")
  public String movieList() {
    return "html/movie/list";
  }

  @PostMapping("")
  @ResponseBody
  public List<MovieResponse> movieList(@RequestBody MovieRequest movieRequest, Model model) {

    return movieApiService.findByKeyword(movieRequest);
  }
}
