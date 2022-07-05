package com.dodatabase.movie_backend.service;

import com.dodatabase.movie_backend.domain.MovieResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieApiService {

    private final MovieApiClient movieApiClient;

    @Transactional(readOnly = true)
    public MovieResponse findByKeyword(String keyword) {
        return movieApiClient.requestMovie(keyword);
    }

}
