package com.dodatabase.movie_backend.domain.Movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponse {
    private String title;
    private int prodYear;
    private String genre;
    private String nation;
    private int runtime;
    private String director;
    private String actor;
}