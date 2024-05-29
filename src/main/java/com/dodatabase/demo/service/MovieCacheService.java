package com.dodatabase.demo.service;

import com.dodatabase.demo.domain.movie.MovieResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MovieCacheService {
  private final Map<Long, MovieResponse> movieCache = new HashMap<>();
  private long currentId = 0;

  public long addMovie(MovieResponse movieResponse) {
    long id = currentId++;
    movieCache.put(id, movieResponse);
    return id;
  }

  public MovieResponse getMovieById(long id) {
    return movieCache.get(id);
  }

  public void clearCache() {
    movieCache.clear();
    currentId = 0;
  }
}
