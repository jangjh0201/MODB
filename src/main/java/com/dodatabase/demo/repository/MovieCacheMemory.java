package com.dodatabase.demo.repository;

import com.dodatabase.demo.domain.movie.MovieResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MovieCacheMemory {

  private final Map<String, MovieResponse> movieCache = new HashMap<>();

  public void addMovieCache(MovieResponse movieResponse) {
    movieCache.put(movieResponse.getId(), movieResponse);
  }

  public MovieResponse getMovieCacheById(String id) {
    return movieCache.get(id);
  }

  public void clearCache() {
    movieCache.clear();
  }
}
