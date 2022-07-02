package com.dodatabase.movie_backend.controller;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.domain.MovieDto;

import com.dodatabase.movie_backend.service.MovieService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class JpaController {

    private final MovieService movieService;

    private final ModelMapper modelMapper;

    @PostMapping("/api/new")
    public ResponseEntity<?> addMovies(@RequestBody MovieDto.Item item) {
        Optional<Movie> byTitle = movieService.findByTitle(item.getTitle());

        try {
            if (byTitle.isPresent()) {
                Exception e = new Exception("에러 발생");
                throw e;
            } else {
                movieService.create(modelMapper.map(item, Movie.class));
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/movies/delete")
    public void removeMovies(@RequestBody MovieDto.Item item) {
        movieService.deleteByTitle(item.getTitle());
    }
}