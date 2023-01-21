package com.dodatabase.movie_backend.domain.Movie;

import java.time.Year;

import lombok.Getter;

@Getter
public class MovieResponseItem {
    private String title;
    private String link;
    private String subtitle;
    private Year pubDate;
    private String director;
    private String actor;
    private float userRating;
}