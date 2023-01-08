package com.dodatabase.movie_backend.service;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.condition.AnyOf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.context.properties.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.dodatabase.movie_backend.TestConfig;
import com.dodatabase.movie_backend.domain.Movie.MovieResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;

@ExtendWith(MockitoExtension.class)
@RestClientTest(MovieApiService.class)
@EnableConfigurationProperties(ApiKey.class)
@Import({ TestConfig.class })
public class MovieApiServiceTest {

    @InjectMocks
    private MovieApiService movieApiService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Mock
    private ApiKey apiKey;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testFindByKeyword() throws Exception {

        // given
        String keyword = "별";

        // String expectedApiUrl = apiKey.getUrl() + "/keyword" + keyword;
        int expectedAge = 32;
        String expectedName = "testName";
        String expectedJsonResponse = "{\"no\":1,\"title\":\"" + keyword + "\",\"age\":" + expectedAge + ",\"name\":\""
                + expectedName + "\"}";

        mockServer.expect(requestTo(keyword))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        // when
        // MovieResponse movieResponse = movieApiService.findByKeyword(keyword);

        // then
        // assertThat(movieResponse.getItems().get(0).getTitle())
        // .contains(keyword);

    }
}
