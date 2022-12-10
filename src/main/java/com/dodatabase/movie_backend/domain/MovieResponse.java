package com.dodatabase.movie_backend.domain;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieResponse {

    private Date lastBuildDate;
    private int total;
    private List<MovieResponseItem> items;

    @Builder
    public MovieResponse(Date lastBuildDate, int total, List<MovieResponseItem> items) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.items = items;
    }
}
