package com.dodatabase.movie_backend.controller;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.domain.MovieResponseDto;
import com.dodatabase.movie_backend.service.MovieApiService;

import com.dodatabase.movie_backend.service.MovieService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class JpaController {

    private final MovieService movieService;

    private final ModelMapper modelMapper;

    @PostMapping("/api/new")
    public void addMovies(@RequestParam("count") int i) {
        MovieResponseDto.Item item = movieService.items[i - 1];
        Optional<Movie> byTitle = movieService.findByTitle(item.getTitle());

        if (byTitle.isPresent()) {
            System.out.println("이미 등록된 영화입니다.");
        } else {
            movieService.create(modelMapper.map(item, Movie.class));
        }
    }

    @PostMapping("/movies/delete")
    public void removeMovies(@RequestParam("title") String title) {
        movieService.deleteByTitle(title);
    }

    // private final MovieApiService movieApiService;
    // private final MovieService movieService;

    // @RequestMapping("/movies/{keyword}")
    // public ResponseEntity<Movie> get(@PathVariable String keyword) {

    // Movie result = null;
    // Optional<Movie> byTitle = movieService.findByTitle(keyword);

    // /**
    // * db에서 keyword조회후 존재하면 db에 있는 값 반환
    // */

    // if (byTitle.isEmpty()) {
    // Movie byKeyword = movieApiService.findByKeyword(keyword);
    // result = movieService.create(byKeyword);
    // } else {
    // result = byTitle.get();
    // }

    // return new ResponseEntity<>(result, HttpStatus.OK);
    // }

    // @GetMapping("/api/v1/movies/{keyword}")
    // public String getForm(@PathVariable String keyword, Model model) {
    // MovieResponseDTO.Item[] items =
    // movieApiService.findByKeyword(keyword).getItems();
    // model.addAttribute("movies", items);

    // for (MovieResponseDTO.Item item : items) {
    // System.out.println("---------------");
    // System.out.println("제목 : " + item.getTitle());
    // System.out.println("링크 : " + item.getLink());
    // System.out.println("부제 : " + item.getSubtitle());
    // System.out.println("개봉 : " + item.getPubDate());
    // System.out.println("감독 : " + item.getDirector());
    // System.out.println("배우 : " + item.getActor());
    // System.out.println("평점 : " + item.getUserRating());
    // }

    // return "api/apiList";
    // }
}