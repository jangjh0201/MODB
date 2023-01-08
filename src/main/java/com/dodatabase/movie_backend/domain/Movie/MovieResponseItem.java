package com.dodatabase.movie_backend.domain.Movie;

import lombok.Getter;

@Getter
public class MovieResponseItem {
    private String title;
    private String link;
    private String subtitle;
    private String pubDate;
    private String director;
    private String actor;
    private float userRating;
}