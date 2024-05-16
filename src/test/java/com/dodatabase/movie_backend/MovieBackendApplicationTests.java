package com.dodatabase.movie_backend;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.dodatabase.movie_backend.service.ApiKey;

@SpringBootTest
@AutoConfigureMockMvc
class MovieBackendApplicationTests {

    @Autowired
    ApiKey apiKey;

    @Test
    public void test() {
        assertThat(apiKey.getKey(), is(equalTo("Hqsz1tECcg712EE903wl")));
    }

}
