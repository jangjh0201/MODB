package com.dodatabase.movie_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.domain.MovieResponseDTO;
import com.dodatabase.movie_backend.service.MovieApiService;
import com.dodatabase.movie_backend.service.MovieService;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private Long sequence = 0L;
    private final MovieApiService movieApiService;
    private final MovieService movieService;
    private MovieResponseDTO.Item[] itemsDto;
    private final Movie movie;

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
        model.addAttribute("movies", items);

        itemsDto = items;

        for (MovieResponseDTO.Item item : items) {
            item.setNumber(num++);
        }

        return "api/apiList";
    }

    @ResponseBody
    @PostMapping("/api/new")
    public void create(@RequestParam("number") int i) {

        MovieResponseDTO.Item item = itemsDto[i - 1];
        String title = item.getTitle().replace("<b>", "").replace("</b>", "");
        Optional<Movie> byTitle = movieService.findByTitle(title);
        if(byTitle.isPresent()){
            return;
        }else{
            movie.setId(++sequence);
            movie.setTitle(itemsDto[i - 1].getTitle().replace("<b>","").replace("</b>", "")); //<b> 같은거 지워줌
            movie.setLink(itemsDto[i - 1].getLink());
            movie.setSubTitle(itemsDto[i - 1].getSubtitle());
            movie.setPubDate(itemsDto[i - 1].getPubDate());
            movie.setDirector(itemsDto[i - 1].getDirector());
            movie.setActor(itemsDto[i - 1].getActor());
            movie.setUserRating(itemsDto[i - 1].getUserRating());

            movieService.create(movie);
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
