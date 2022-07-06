package com.dodatabase.movie_backend.domain;

import lombok.Data;

@Data
public class MovieResponse {

    private Item[] items;

    @Data
    public static class Item {
        private String title;
        private String link;
        private String subtitle;
        private String pubDate;
        private String director;
        private String actor;
        private float userRating;
    }

}
