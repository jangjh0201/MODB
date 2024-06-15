// package com.dodatabase.demo.service;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertTrue;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import com.dodatabase.demo.domain.movie.Movie;
// import com.dodatabase.demo.domain.movie.MovieData;
// import com.dodatabase.demo.repository.MovieRepository;
// import java.time.Year;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// public class FavoriteServiceTest {

// @Mock
// private MovieRepository movieRepository;

// @InjectMocks
// private FavoriteService favoriteService;

// private Movie movie;

// @BeforeEach
// void setUp() {
// MockitoAnnotations.openMocks(this);
// movie = Movie.builder("F10538")
// .title("스타워즈 에피소드 3 : 시스의 복수")
// .prodYear(Year.of(2005))
// .genre("SF")
// .nation("미국")
// .runtime(121)
// .director("조지 루카스")
// .actor("한 솔로")
// .build();
// }

// @Test
// void createMovieTest() {
// favoriteService.create(movie);
// verify(movieRepository, times(1)).save(movie);
// }

// @Test
// void findMoviesTest() {
// when(movieRepository.findAll()).thenReturn(Arrays.asList(movie));
// List<Movie> movies = favoriteService.findMovies();
// assertEquals(1, movies.size());
// assertEquals(movie.getTitle(), movies.get(0).getTitle());
// }

// @Test
// void findByTitleTest() {
// when(movieRepository.findByTitle("스타워즈")).thenReturn(Optional.of(movie));
// Optional<Movie> foundMovie = favoriteService.findById("A00000");
// assertTrue(foundMovie.isPresent());
// assertEquals(movie.getTitle(), foundMovie.get().getTitle());
// }

// @Test
// void deleteByIdTest() {
// favoriteService.deleteById("A00000");
// verify(movieRepository, times(1)).deleteById("A00000");
// }
// }
