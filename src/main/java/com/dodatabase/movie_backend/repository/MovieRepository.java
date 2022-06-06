package com.dodatabase.movie_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dodatabase.movie_backend.domain.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MovieRepository extends JpaRepository<Movie, Long> {

     Optional<Movie> findByTitle(String keyword);

}
