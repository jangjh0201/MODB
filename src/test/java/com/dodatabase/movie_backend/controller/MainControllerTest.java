package com.dodatabase.movie_backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
import com.dodatabase.movie_backend.service.ExternalApiService;
import com.dodatabase.movie_backend.service.WishListService;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(MainController.class)
@WebAppConfiguration
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExternalApiService externalApiService;

    @MockBean
    private WishListService wishListService;

    @Test
    public void searchApiFormTest() throws Exception {
        mockMvc.perform(get("/api/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("api/apiSearchForm"))
                .andDo(print());
    }

    @Test
    public void searchApiTest() throws Exception {
        String nation = "미국";
        String genre = "SF";
        String title = "스타워즈";

        MovieResponse movieResponse = MovieResponse.builder()
                .title("스타워즈")
                .prodYear(1977)
                .genre("SF")
                .nation("미국")
                .runtime(121)
                .director("조지 루카스")
                .actor("한 솔로")
                .build();

        List<MovieResponse> movieResponseList = Collections.singletonList(movieResponse);

        // given
        given(externalApiService.findByKeyword(nation, genre, title)).willReturn(movieResponseList);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/search")
                .param("nation", nation)
                .param("genre", genre)
                .param("title", title)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("api/html/apiList"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", movieResponseList))
                .andDo(print());
    }

    @Test
    public void listMoviesTest() throws Exception {
        // given
        given(wishListService.findMovies()).willReturn(Collections.emptyList());

        // when
        ResultActions resultActions = mockMvc.perform(get("/movie"))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("movie/html/movieList"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", Collections.emptyList()))
                .andDo(print());
    }
}
