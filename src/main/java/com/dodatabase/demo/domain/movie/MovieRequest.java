package com.dodatabase.demo.domain.movie;

import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MovieRequest that = (MovieRequest) o;
    return Objects.equals(nation, that.nation)
        && Objects.equals(genre, that.genre)
        && Objects.equals(title, that.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nation, genre, title);
  }
}
