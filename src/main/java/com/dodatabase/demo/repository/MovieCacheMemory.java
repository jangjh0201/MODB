package com.dodatabase.demo.repository;

import com.dodatabase.demo.domain.movie.Movie;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MovieCacheMemory {

  private final Map<String, Movie> movieCache = new HashMap<>();

  public void addMovieCache(Movie movie) {
    movieCache.put(movie.getId(), movie);
  }

  public Movie getMovieCacheById(String id) {
    return movieCache.get(id);
  }

  public void clearCache() {
    movieCache.clear();
  }
}
