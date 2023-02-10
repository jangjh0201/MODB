package com.dodatabase.movie_backend.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dodatabase.movie_backend.domain.Movie.GenreType;
import com.dodatabase.movie_backend.domain.Movie.CountryType;
import com.dodatabase.movie_backend.domain.Movie.Movie;
import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
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
        return "index";
    }

    @GetMapping("/api/search")
    public ModelAndView searchApiForm() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("api/apiSearchForm");
        mv.addObject("countries", CountryType.values());
        mv.addObject("genres", GenreType.values());
        return mv;
    }

    @PostMapping("/api/search")
    public ModelAndView searchApi(
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "query") String query) {
        MovieResponse items = movieApiService.findByKeyword(country, genre, query);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("api/apiList");
        mv.addObject("countries", CountryType.values());
        mv.addObject("genres", GenreType.values());
        mv.addObject("movies", items.getItems());

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
