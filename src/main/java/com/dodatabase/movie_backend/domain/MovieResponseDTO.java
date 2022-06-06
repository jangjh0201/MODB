package com.dodatabase.movie_backend.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MovieResponseDTO {

    private Item[] items;

    @Data
    public static class Item {
        public int number;
        public String title;
        public String link;
        public String subtitle;
        public String pubDate;
        public String director;
        public String actor;
        public float userRating;
    }
}
