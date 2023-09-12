package com.dodatabase.movie_backend.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String searchApiForm(Model mv) {
        mv.addAttribute("countries", CountryType.values());
        mv.addAttribute("genres", GenreType.values());
        return "api/apiSearchForm";
    }

    @PostMapping("/api/search")
    public String searchApi(
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "query") String query,
            Model mv) {
        MovieResponse items = movieApiService.findByKeyword(country, genre, query);

        mv.addAttribute("countries", CountryType.values());
        mv.addAttribute("genres", GenreType.values());
        mv.addAttribute("movies", items.getItems());

        return "api/apiList";
    }

    @GetMapping("/movies")
    public String list(Model mv) {
        List<Movie> movies = movieService.findMovies();
        mv.addAttribute("movies", movies);

        return "movies/movieList";
    }

}
