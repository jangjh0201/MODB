package com.dodatabase.backend.repository;

import com.dodatabase.backend.domain.wish.Wish;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, String> {

  // Movie findById(Movie movie);

  Optional<Wish> findByTitle(String title);

  // List<Movie> findAll();

  // List<Movie> Save(Movie movie);

  void deleteById(@NonNull String id);

}
