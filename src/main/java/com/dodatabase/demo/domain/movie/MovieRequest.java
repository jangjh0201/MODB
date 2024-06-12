package com.dodatabase.demo.domain.movie;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MovieRequest {

  String nation;
  String genre;
  String title;

  @Builder(builderClassName = "MovieRequestBuilder", builderMethodName = "movieRequestBuilder")
  private MovieRequest(String nation, String genre, String title) {
    this.nation = nation;
    this.genre = genre;
    this.title = title;
  }

  public static MovieRequestBuilder builder() {
    return movieRequestBuilder();
  }
}
