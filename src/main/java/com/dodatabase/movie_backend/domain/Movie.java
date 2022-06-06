package com.dodatabase.movie_backend.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Entity
@Data @NoArgsConstructor
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;

    public String link;

    public String subTitle;

    public String pubDate;

    public String director;

    public String actor;

    public float userRating;


}
