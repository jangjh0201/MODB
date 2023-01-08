package com.dodatabase.movie_backend.domain.Movie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private String title;
    private String link;
    private String subtitle;
    private String pubDate;
    private String director;
    private String actor;
    private float userRating;

    @Builder
    public Movie(Long movieId, String title, String link, String subtitle,
            String pubDate, String director, String actor, float userRating) {
        this.movieId = movieId;
        this.title = title;
        this.link = link;
        this.subtitle = subtitle;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
        this.userRating = userRating;
    }

}
