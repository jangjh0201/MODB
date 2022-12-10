package com.dodatabase.movie_backend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponseItem {
    private String title;
    private String link;
    private String subtitle;
    private String pubDate;
    private String director;
    private String actor;
    private float userRating;
}