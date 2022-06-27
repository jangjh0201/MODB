package com.dodatabase.movie_backend.controller;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.domain.MovieDto;

import com.dodatabase.movie_backend.service.MovieService;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class JpaController {

    private final MovieService movieService;

    private final ModelMapper modelMapper;

    @PostMapping("/api/new")
    public void addMovies(@RequestBody MovieDto.Item item) {
        Optional<Movie> byTitle = movieService.findByTitle(item.getTitle());

        if (byTitle.isPresent()) {
            System.out.println("이미 등록된 영화입니다.");
        } else {
            movieService.create(modelMapper.map(item, Movie.class));
        }
    }

    @PostMapping("/movies/delete")
    public void removeMovies(@RequestBody MovieDto.Item item) {
        movieService.deleteByTitle(item.getTitle());
    }
}