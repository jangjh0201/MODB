package com.dodatabase.backend.domain.wish;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishRequest {
  private String id;
  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;
  private WishDetail detail;

  @Builder(builderClassName = "WishRequestBuilder", builderMethodName = "wishRequestBuilder")
  public WishRequest(String id, String title, int prodYear, String genre,
      String nation, int runtime, String director, String actor, WishDetail detail) {
    this.id = id;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.director = director;
    this.actor = actor;
    this.detail = detail;
  }

  public static WishRequestBuilder builder() {
    return wishRequestBuilder();
  }
}
