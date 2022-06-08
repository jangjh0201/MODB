package com.dodatabase.movie_backend.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.dodatabase.movie_backend.domain.Movie;
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
    public String searchApi(@RequestParam("keyword") String keyword, Model model) {
        movieService.items = movieApiService.findByKeyword(keyword).getItems();
        model.addAttribute("movies", movieService.items);

        return "api/apiList";
    }

    @GetMapping("/movies")
    public String list(Model model) {
        List<Movie> movies = movieService.findMovies();
        model.addAttribute("movies", movies);
        return "movies/movieList";
    }

}
