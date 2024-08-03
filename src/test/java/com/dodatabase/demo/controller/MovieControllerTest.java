package com.dodatabase.demo.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodatabase.demo.domain.movie.MovieDetail;
import com.dodatabase.demo.domain.movie.MovieRequest;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.service.MovieService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MovieService movieService;

  private MovieRequest movieRequest;
  private MovieResponse movieResponse;
  private List<MovieResponse> movieResponseList;

  @BeforeEach
  void initialize() {
    movieRequest = MovieRequest.builder()
        .nation("미국")
        .genre("SF")
        .title("스타워즈")
        .build();

    movieResponse = MovieResponse.builder()
        .id("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .detail(MovieDetail.builder()
            .posters(Arrays.asList("http://file.koreafilm.or.kr/thm/02/00/02/63/tn_DPF006410.JPG"))
            .plot("클론 전쟁이 시작되었던 때로부터 3년이 지나고...")
            .build())
        .build();

    movieResponseList = Collections.singletonList(movieResponse);
  }

  @Test
  public void searchApiGetTest() throws Exception {
    given(movieService.findByKeyword(movieRequest)).willReturn(movieResponseList);

    mockMvc.perform(get("/v1/movie")
        .param("nation", "미국")
        .param("genre", "SF")
        .param("title", "스타워즈"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("F10538"))
        .andExpect(jsonPath("$[0].title").value("스타워즈 에피소드 3 : 시스의 복수"))
        .andExpect(jsonPath("$[0].prodYear").value(2005))
        .andExpect(jsonPath("$[0].genre").value("액션,SF,어드벤처,판타지"))
        .andExpect(jsonPath("$[0].nation").value("미국"))
        .andExpect(jsonPath("$[0].runtime").value(139))
        .andExpect(jsonPath("$[0].director").value("조지 루카스"))
        .andExpect(jsonPath("$[0].actor").value("이완 맥그리거"))
        .andExpect(
            jsonPath("$[0].detail.posters[0]").value("http://file.koreafilm.or.kr/thm/02/00/02/63/tn_DPF006410.JPG"))
        .andExpect(jsonPath("$[0].detail.plot").value("클론 전쟁이 시작되었던 때로부터 3년이 지나고..."))
        .andDo(print());
  }
}
