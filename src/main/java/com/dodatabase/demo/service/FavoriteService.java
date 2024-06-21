package com.dodatabase.demo.service;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.wishlist.WishRequest;
import com.dodatabase.demo.domain.wishlist.WishResponse;
import com.dodatabase.demo.repository.MovieRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final MovieRepository movieRepository;

  @Transactional
  public void create(WishRequest wishRequest) {
    Movie movie = convertToMovie(wishRequest);
    movieRepository.save(movie);
  }

  @Transactional(readOnly = true)
  public List<WishResponse> findMovies() {
    List<Movie> movies = movieRepository.findAll();
    return movies.stream()
        .map(this::convertToWishResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<WishResponse> findById(String id) {
    Optional<Movie> movie = movieRepository.findById(id);
    return movie.map(this::convertToWishResponse);
  }

  @Transactional
  public void deleteById(String id) {
    movieRepository.deleteById(id);
  }

  private Movie convertToMovie(WishRequest wishRequest) {
    return Movie.builder(wishRequest.getId())
        .title(wishRequest.getTitle())
        .prodYear(wishRequest.getProdYear())
        .genre(wishRequest.getGenre())
        .nation(wishRequest.getNation())
        .runtime(wishRequest.getRuntime())
        .director(wishRequest.getDirector())
        .actor(wishRequest.getActor())
        .build();
  }

  private WishResponse convertToWishResponse(Movie movie) {
    return WishResponse.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .prodYear(movie.getProdYear())
        .genre(movie.getGenre())
        .nation(movie.getNation())
        .runtime(movie.getRuntime())
        .director(movie.getDirector())
        .actor(movie.getActor())
        .build();
  }
}
