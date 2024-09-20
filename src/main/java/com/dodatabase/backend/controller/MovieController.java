package com.dodatabase.backend.controller;

import com.dodatabase.backend.domain.movie.MovieRequest;
import com.dodatabase.backend.domain.movie.MovieResponse;
import com.dodatabase.backend.service.MovieService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/movie")
public class MovieController {

  private final MovieService movieService;

  @GetMapping("")
  @ResponseBody
  public List<MovieResponse> movies(
      @RequestParam(value = "nation", required = false) String nation,
      @RequestParam(value = "genre", required = false) String genre,
      @RequestParam(value = "title") String title) {

    return movieService.findByKeyword(
        MovieRequest.builder()
            .nation(nation)
            .genre(genre)
            .title(title)
            .build());
  }
}
