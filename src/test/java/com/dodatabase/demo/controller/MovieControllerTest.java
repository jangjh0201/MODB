package com.dodatabase.demo.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.dodatabase.demo.domain.movie.GenreType;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.domain.movie.NationType;
import com.dodatabase.demo.repository.MovieCacheMemory;
import com.dodatabase.demo.service.MovieApiService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MovieApiService movieApiService;

  @MockBean
  private MovieCacheMemory movieCacheMemory;

  private MovieResponse movieResponse;
  private List<MovieResponse> movieResponseList;

  @BeforeEach
  void setUp() {
    movieResponse = MovieResponse.builder()
        .title("스타워즈")
        .prodYear(1977)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("한 솔로")
        .build();

    movieResponseList = Collections.singletonList(movieResponse);
  }

  @Test
  public void searchApiGetTest() throws Exception {
    mockMvc.perform(get("/v1/movies"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("html/movie/list"))
        .andDo(print());
  }

  @Test
  public void searchApiPostTest() throws Exception {
    String nation = "미국";
    String genre = "SF";
    String title = "스타워즈";

    // given
    given(movieApiService.findByKeyword(nation, genre, title)).willReturn(movieResponseList);

    // when
    ResultActions resultActions = mockMvc.perform(post("/v1/movies")
        .param("nation", nation)
        .param("genre", genre)
        .param("title", title)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andDo(print());

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(view().name("html/movie/list"))
        .andExpect(model().attributeExists("nations"))
        .andExpect(model().attributeExists("genres"))
        .andExpect(model().attributeExists("movies"))
        .andExpect(model().attribute("nations", NationType.values()))
        .andExpect(model().attribute("genres", GenreType.values()))
        .andExpect(model().attribute("movies", movieResponseList))
        .andDo(print());
  }
}
