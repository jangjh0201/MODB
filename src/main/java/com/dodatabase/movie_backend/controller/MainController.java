package com.dodatabase.movie_backend.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.domain.MovieResponseDto;
import com.dodatabase.movie_backend.service.MovieApiService;
import com.dodatabase.movie_backend.service.MovieService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MovieApiService movieApiService;
    private final MovieService movieService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/api/search")
    public String searchApiForm() {
        return "api/apiSearchForm";
    }

    @PostMapping("/api/search")
    public ModelAndView searchApi(@RequestParam("keyword") String keyword) {
        MovieResponseDto.Item[] items = movieApiService.findByKeyword(keyword).getItems();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("api/apiList");
        mv.addObject("movies", items);

        return mv;
    }

    @GetMapping("/movies")
    public ModelAndView list() {
        List<Movie> movies = movieService.findMovies();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("movies/movieList");
        mv.addObject("movies", movies);

        return mv;
    }

}
