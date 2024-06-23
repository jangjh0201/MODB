package com.dodatabase.demo.domain.wish;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WishResponse {
  private String id;
  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;

  @Builder(builderClassName = "WishResponseBuilder", builderMethodName = "wishResponseBuilder")
  public WishResponse(String id, String title, int prodYear, String genre,
      String nation, int runtime, String director, String actor) {
    this.id = id;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.director = director;
    this.actor = actor;
  }

  public static WishResponseBuilder builder() {
    return wishResponseBuilder();
  }
}
