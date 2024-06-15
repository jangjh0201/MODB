package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.MovieRequest;
import com.dodatabase.demo.domain.movie.MovieResponse;
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

  @GetMapping("")
  public String searchApi() {
    return "html/movie/list";
  }

  @PostMapping("")
  public String searchApi(
      @RequestParam(value = "nation", required = false) String nation,
      @RequestParam(value = "genre", required = false) String genre,
      @RequestParam(value = "title") String title, Model model) {

    // 나중에 파리미터로 @RequestBody로 받아서 바로 전달하는 구조로 개선할 것
    List<MovieResponse> movieResponseList = movieApiService.findByKeyword(MovieRequest.builder()
        .nation(nation)
        .genre(genre)
        .title(title)
        .build());

    model.addAttribute("movies", movieResponseList);

    return "html/movie/list";
  }
}
