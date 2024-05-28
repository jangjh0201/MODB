package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.GenreType;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.domain.movie.NationType;
import com.dodatabase.demo.service.ExternalApiService;
import com.dodatabase.demo.service.MovieCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ExternalApiController {

  private final ExternalApiService externalApiService;
  private final MovieCacheService movieCacheService;

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

    // 캐시 초기화
    movieCacheService.clearCache();

    // 캐시에 영화 데이터를 저장하고 ID를 매핑합니다.
    List<Long> movieIds = results.stream()
        .map(movieCacheService::addMovie)
        .collect(Collectors.toList());

    model.addAttribute("nations", NationType.values());
    model.addAttribute("genres", GenreType.values());
    model.addAttribute("movies", results);
    model.addAttribute("movieIds", movieIds);

    return "api/html/apiList";
  }
}
