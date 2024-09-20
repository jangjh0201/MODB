package com.dodatabase.backend.domain.movie;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MovieDetail {
  private List<String> posters;
  private String plot;

  @Builder(builderClassName = "MovieDetailBuilder", builderMethodName = "movieDetailBuilder")
  public MovieDetail(List<String> posters, String plot) {
    this.posters = posters;
    this.plot = plot;
  }

  public static MovieDetailBuilder builder() {
    return movieDetailBuilder();
  }
}
