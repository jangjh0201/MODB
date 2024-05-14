package com.dodatabase.movie_backend.domain.Movie;

import lombok.Getter;

@Getter
public enum NationType {
    대한민국("대한민국"),
    미국("미국"),
    일본("일본"),
    홍콩("홍콩"),
    영국("영국"),
    프랑스("프랑스");

    private String code;

    NationType(String code) {
        this.code = code;
    }
}