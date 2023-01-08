package com.dodatabase.movie_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dodatabase.movie_backend.domain.Movie.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Movie findById(Movie movie);

    Optional<Movie> findByTitle(String keyword);

    // List<Movie> findAll();

    // List<Movie> Save(Movie movie);

    void deleteByTitle(String title);

}
