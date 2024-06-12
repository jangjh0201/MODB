package com.dodatabase.demo.domain.movie;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MovieData {
  private final String id;
  private final String title;
  private final Year prodYear;
  private final String genre;
  private final String nation;
  private final int runtime;
  private final List<Director> directors;
  private final List<Actor> actors;

  @Builder(builderClassName = "MovieDataBuilder", builderMethodName = "movieDataBuilder")
  private MovieData(String id, String title, Year prodYear, String genre,
      String nation, int runtime, List<Director> directors, List<Actor> actors) {
    this.id = id;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.directors = directors;
    this.actors = actors;
  }

  public static MovieDataBuilder builder() {
    return movieDataBuilder();
  }

  public String getDirectorNames() {
    return directors.stream()
        .map(Director::getDirectorNm)
        .collect(Collectors.joining(", "));
  }

  public String getActorNames() {
    return actors.stream()
        .map(Actor::getActorNm)
        .collect(Collectors.joining(", "));
  }

  @Getter
  @Builder
  public static class Director {
    private String directorNm;
    private String directorEnNm;
    private String directorId;
  }

  @Getter
  @Builder
  public static class Actor {
    private String actorNm;
    private String actorEnNm;
    private String actorId;
  }
}
