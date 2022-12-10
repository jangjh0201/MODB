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

import com.dodatabase.movie_backend.domain.MovieResponse;
import com.dodatabase.movie_backend.service.MovieApiService;
import com.dodatabase.movie_backend.service.MovieService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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

    @Test
    public void searchApiFormTest() throws Exception {
        mockMvc.perform(get("/api/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andDo(print());
    }

    @Test
    public void searchApiTest() throws Exception {
        String param = "keyword";
        String value = "star";

        // MultiValueMap<String, MovieResponse.Item> multiValueMap = new
        // LinkedMultiValueMap<>();
        // multiValueMap.put("moveResponseID", List<MovieResponse.Item[]> movieItems);

        // given
        given(movieApiService.findByKeyword(value)).willReturn(new MovieResponse());

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/search")
                .param(param, value))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());

    }
}
