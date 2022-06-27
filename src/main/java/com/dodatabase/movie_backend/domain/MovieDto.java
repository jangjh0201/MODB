package com.dodatabase.movie_backend.domain;

import lombok.Data;

@Data
public class MovieDto {

    private Item[] items;

    @Data
    public static class Item {
        public String title;
        public String link;
        public String subtitle;
        public String pubDate;
        public String director;
        public String actor;
        public float userRating;
    }

}
