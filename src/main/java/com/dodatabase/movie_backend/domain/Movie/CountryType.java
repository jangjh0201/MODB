package com.dodatabase.movie_backend.domain.Movie;

import lombok.Getter;

@Getter
public enum CountryType {
    프랑스("FR"),
    영국("GB"),
    홍콩("HK"),
    일본("JP"),
    한국("KR"),
    미국("US"),
    기타("ETC");

    private String code;

    CountryType(String code) {
        this.code = code;
    }
}