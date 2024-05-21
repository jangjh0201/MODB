package com.dodatabase.movie_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.dodatabase.movie_backend.config.ApiKey;

@SpringBootTest
@AutoConfigureMockMvc
class MovieBackendApplicationTests {

    @Autowired
    ApiKey apiKey;

}
