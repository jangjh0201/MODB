package com.dodatabase.movie_backend.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.dodatabase.movie_backend.domain.Movie.GenreType;
import com.dodatabase.movie_backend.domain.Movie.NationType;
import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
import com.dodatabase.movie_backend.domain.Movie.Movie;
import com.dodatabase.movie_backend.service.ExternalApiService;
import com.dodatabase.movie_backend.service.WishListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ExternalApiService movieApiService;
    private final WishListService movieService;

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
        List<MovieResponse> results = movieApiService.findByKeyword(nation, genre, title);

        model.addAttribute("movies", results);

        return "api/html/apiList";
    }

    @GetMapping("/movie")
    public String list(Model model) {
        List<Movie> movies = movieService.findMovies();
        model.addAttribute("movies", movies);

        return "movie/html/movieList";
    }

}
