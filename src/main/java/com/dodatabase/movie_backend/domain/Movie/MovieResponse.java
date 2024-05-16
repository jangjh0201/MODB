package com.dodatabase.movie_backend.domain.Movie;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MovieResponse {

    private int total;
    private List<MovieResponseItem> items;

    @Builder
    public MovieResponse(int total, List<MovieResponseItem> items) {
        this.total = total;
        this.items = items;
    }
}
