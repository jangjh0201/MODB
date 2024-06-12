package com.dodatabase.demo.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.dodatabase.demo.domain.movie.MovieData;
import com.dodatabase.demo.domain.movie.MovieRequest;
import com.dodatabase.demo.service.MovieApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Year;
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

  private MovieRequest movieRequest;
  private MovieData movieData;
  private List<MovieData> movieDataList;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void initialize() {
    movieRequest = MovieRequest.builder()
        .nation("미국")
        .genre("SF")
        .title("스타워즈")
        .build();

    movieData = MovieData.builder()
        .id("A00000")
        .title("스타워즈")
        .prodYear(Year.of(1977))
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .directors(Collections.singletonList(MovieData.Director.builder()
            .directorNm("조지 루카스")
            .directorEnNm("George Lucas")
            .directorId("00049192")
            .build()))
        .actors(Collections.singletonList(MovieData.Actor.builder()
            .actorNm("한 솔로")
            .actorEnNm("Han Solo")
            .actorId("00049011")
            .build()))
        .build();

    movieDataList = Collections.singletonList(movieData);
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

    // given
    given(movieApiService.findMovie(movieRequest)).willReturn(movieDataList);

    // when
    String movieRequestJson = objectMapper.writeValueAsString(movieRequest);
    ResultActions resultActions = mockMvc.perform(post("/v1/movies")
        .content(movieRequestJson)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(view().name("html/movie/list"))
        .andExpect(model().attributeExists("movies"))
        .andExpect(model().attribute("movies", movieDataList))
        .andDo(print());
  }
}
