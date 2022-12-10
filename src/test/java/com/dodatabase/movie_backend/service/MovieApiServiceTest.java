package com.dodatabase.movie_backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dodatabase.movie_backend.domain.Movie;
import com.dodatabase.movie_backend.domain.MovieResponse;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

public class MovieApiServiceTest {

    @MockBean
    MovieApiClient movieApiClient;

    // @Test
    // public void testGetMapping() throws Exception {
    // mockMvc.perform(get("/api/search"))
    // .andExpect(status().isOk())
    // .andDo(print());
    // }

    @Test
    public void testFindByKeyword() throws Exception {
        // System.out.println(movieApiClient.requestMovie("star"));

        // given
        String keyword = "star";
        given(movieApiClient.requestMovie(any())).willReturn(new MovieResponse());

        // when
        MovieResponse movieResponse = movieApiClient.requestMovie(keyword);

        // then
        // assertEquals(movieResponse.getItems());
    }
}
