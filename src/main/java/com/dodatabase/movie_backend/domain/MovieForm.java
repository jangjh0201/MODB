package com.dodatabase.movie_backend.domain;

import java.util.Date;

import lombok.Data;
import lombok.Getter;

@Data
public class MovieForm {

    private String title = null;

    private String link = null;

    private String subTitle = null;

    private Date pubDate = null;

    private String director = null;

    private String actor = null;

    private float userRating = 0;
}
