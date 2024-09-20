package com.dodatabase.backend.repository;

import com.dodatabase.backend.domain.wish.Wish;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MovieCacheMemory {

  private final Map<String, Wish> movieCache = new HashMap<>();

  public void addMovieCache(Wish movie) {
    movieCache.put(movie.getId(), movie);
  }

  public Wish getMovieCacheById(String id) {
    return movieCache.get(id);
  }

  public void clearCache() {
    movieCache.clear();
  }
}
