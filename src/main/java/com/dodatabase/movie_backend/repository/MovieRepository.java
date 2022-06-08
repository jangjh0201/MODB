package com.dodatabase.movie_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dodatabase.movie_backend.domain.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Movie findById(Movie movie);

    Optional<Movie> findByTitle(String keyword);

    // List<Movie> findAll();

    // List<Movie> Save(Movie movie);

    void deleteByTitle(String title);

}
