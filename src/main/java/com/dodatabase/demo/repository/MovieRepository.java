package com.dodatabase.demo.repository;

import com.dodatabase.demo.domain.movie.Movie;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

  // Movie findById(Movie movie);i

  Optional<Movie> findByTitle(String keyword);

  // List<Movie> findAll();

  // List<Movie> Save(Movie movie);

  void deleteById(String id);

}
