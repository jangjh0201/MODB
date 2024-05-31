package com.dodatabase.demo.service;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.repository.MovieRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishListService {

  private final MovieRepository movieRepository;

  @Transactional
  public void create(Movie movie) {
    movieRepository.save(movie);
  }

  @Transactional(readOnly = true)
  public List<Movie> findMovies() {
    return movieRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Movie> findByTitle(String keyword) {
    return movieRepository.findByTitle(keyword);
  }

  @Transactional
  public void deleteById(Long id) {
    movieRepository.deleteById(id);
  }
}
