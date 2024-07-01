package com.dodatabase.demo.domain.movie;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MovieResponse {
  private String id;
  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;
  private MovieDetail movieDetail;

  @Builder(builderClassName = "MovieResponseBuilder", builderMethodName = "movieResponseBuilder")
  public MovieResponse(String id, String title, int prodYear, String genre,
      String nation, int runtime, String director, String actor, MovieDetail movieDetail) {
    this.id = id;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.director = director;
    this.actor = actor;
    this.movieDetail = movieDetail;
  }

  public static MovieResponseBuilder builder() {
    return movieResponseBuilder();
  }
}
