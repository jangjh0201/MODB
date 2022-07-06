package com.dodatabase.movie_backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public void create(Movie movie) {
        movieRepository.save(movie);
    }

    /**
     * 전체 도서 목록 조회
     */
    public List<Movie> findMovies() {
        return movieRepository.findAll();
    }

    @Transactional()
    public void deleteByTitle(String title) {
        movieRepository.deleteByTitle(title);
    }

    public Optional<Movie> findByTitle(String keyword) {
        return movieRepository.findByTitle(keyword);
    }
}
