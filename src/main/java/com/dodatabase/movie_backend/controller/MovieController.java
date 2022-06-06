package com.dodatabase.movie_backend.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.domain.MovieResponseDTO;
import com.dodatabase.movie_backend.service.MovieApiService;
import com.dodatabase.movie_backend.service.MovieService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieApiService movieApiService;
    private final MovieService movieService;
    private final ModelMapper modelMapper;

    private MovieResponseDTO.Item[] itemsDto;

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
        int num = 1;
        MovieResponseDTO.Item[] items = movieApiService.findByKeyword(keyword).getItems();

        for (MovieResponseDTO.Item item : items) {
            item.setTitle(item.getTitle().replace("<b>", "").replace("</b>", ""));
            item.setActor(item.getActor().replace("|"," "));
            item.setDirector(item.getDirector().replace("|", " "));
            item.setNumber(num++);
        }

        model.addAttribute("movies", items);
        itemsDto = items;

        return "api/apiList";
    }

    @ResponseBody
    @PostMapping("/api/new")
    public void create(@RequestParam("number") int i) {

        MovieResponseDTO.Item item = itemsDto[i - 1];
        Optional<Movie> byTitle = movieService.findByTitle(item.getTitle());
        if(byTitle.isPresent()){
            return;
        }else{
            movieService.create(modelMapper.map(item,Movie.class));
        }
    }

    @GetMapping("/movies")
    public String list(Model model) {
        List<Movie> items = movieService.findMovies();
        System.out.println(items);
        model.addAttribute("movies", items);
        return "movies/movieList";
    }


    @DeleteMapping("/movies/delete")
    public String remove(@RequestParam String title){
        Optional<Movie> byTitle = movieService.findByTitle(title);
        if(byTitle.isPresent()){
            Movie movie = byTitle.get();
            movieService.removeWish(movie);
        }
        return "movies/movieList";
    }
}
