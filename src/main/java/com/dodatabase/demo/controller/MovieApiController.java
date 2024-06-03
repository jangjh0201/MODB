package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.movie.GenreType;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.domain.movie.NationType;
import com.dodatabase.demo.repository.MovieCacheMemory;
import com.dodatabase.demo.service.MovieApiService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class MovieApiController {

  private final MovieApiService movieApiService;
  private final MovieCacheMemory movieCacheMemory;

  @GetMapping("/search")
  public String searchApi() {
    return "html/search/list";
  }

  @PostMapping("/search")
  public String searchApi(
      @RequestParam(value = "nation", required = false) String nation,
      @RequestParam(value = "genre", required = false) String genre,
      @RequestParam(value = "title") String title,
      Model model) {
    List<MovieResponse> results = movieApiService.findByKeyword(nation, genre, title);

    // 캐시 초기화
    movieCacheMemory.clearCache();

    // 캐시에 영화 데이터를 저장하고 ID를 매핑합니다.
    results.stream()
        .map(movieCacheMemory::addMovie)
        .collect(Collectors.toList());

    model.addAttribute("nations", NationType.values());
    model.addAttribute("genres", GenreType.values());
    model.addAttribute("movies", results);

    return "html/search/list";
  }
}
