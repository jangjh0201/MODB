package com.dodatabase.demo.repository;

import com.dodatabase.demo.domain.wish.Wish;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, String> {

  // Movie findById(Movie movie);i

  Optional<Wish> findByTitle(String title);

  // List<Movie> findAll();

  // List<Movie> Save(Movie movie);

  void deleteById(String id);

}
