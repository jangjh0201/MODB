package com.dodatabase.demo.domain.movie;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MovieResponse {
  private String docId;
  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;

  @Builder(builderClassName = "MovieResponseBuilder", builderMethodName = "movieResponseBuilder")
  public MovieResponse(String docId, String title, int prodYear, String genre, String nation, int runtime,
      String director,
      String actor) {
    this.docId = docId;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.director = director;
    this.actor = actor;
  }

  public static MovieResponseBuilder builder() {
    return movieResponseBuilder();
  }
}
