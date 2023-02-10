package com.dodatabase.movie_backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dodatabase.movie_backend.domain.Movie.MovieResponse;
import com.dodatabase.movie_backend.domain.Movie.MovieResponseItem;
import com.dodatabase.movie_backend.service.MovieApiService;
import com.dodatabase.movie_backend.service.MovieService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(MainController.class)
@WebAppConfiguration
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieApiService movieApiService;

    @MockBean
    private MovieService movieService;

    @MockBean
    private MovieResponse movieResponse;

    @Test
    public void searchApiFormTest() throws Exception {
        mockMvc.perform(get("/api/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andDo(print());
    }

    @Test
    public void searchApiTest() throws Exception {
        String country = "JP";
        String genre = "15";
        String query = "star";

        MultiValueMap<String, MovieResponseItem> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("moveResponseID", movieResponse.getItems());

        // given
        given(movieApiService.findByKeyword(country, genre, query)).willReturn(new MovieResponse());

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/search")
                .param("country", country)
                .param("genre", genre)
                .param("query", query))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());

    }
}