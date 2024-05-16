package com.dodatabase.movie_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.dodatabase.movie_backend.service.ApiKey;

import lombok.Builder;

@Configuration
public class WebClientConfig {

    private static final String KMDB_HOST = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";

    private final ApiKey apiKey;

    @Builder
    public WebClientConfig(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    @Bean
    public WebClient movieApiClient() {
        String HOST = KMDB_HOST + "&ServiceKey=" + apiKey.getKey();
        return WebClient.builder()
                .baseUrl(HOST)
                .build();
    }
}
