package com.dodatabase.movie_backend.service;

import java.util.List;
import java.util.Optional;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Long create(Movie movie) {
        System.out.println(movie);
        movieRepository.save(movie);
        return movie.getId();
    }

    /**
     * 전체 도서 목록 조회
     */
    public List<Movie> findMovies() {
        return movieRepository.findAll();
    }
    public Optional<Movie> findByTitle(String keyword) {
        return movieRepository.findByTitle(keyword);
    }
    public void removeWish(Movie movie){
        movieRepository.delete(movie);
    }


}
