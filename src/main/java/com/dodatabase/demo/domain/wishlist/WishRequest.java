package com.dodatabase.demo.domain.wishlist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishRequest {
  private String id;
  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;

  @Builder(builderClassName = "WishRequestBuilder", builderMethodName = "wishRequestBuilder")
  @JsonCreator
  public WishRequest(@JsonProperty("id") String id,
      @JsonProperty("title") String title,
      @JsonProperty("prodYear") int prodYear,
      @JsonProperty("genre") String genre,
      @JsonProperty("nation") String nation,
      @JsonProperty("runtime") int runtime,
      @JsonProperty("director") String director,
      @JsonProperty("actor") String actor) {
    this.id = id;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.director = director;
    this.actor = actor;
  }

  public static WishRequestBuilder builder() {
    return wishRequestBuilder();
  }
}
