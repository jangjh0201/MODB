package com.dodatabase.demo.domain.movie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;

  @Builder(builderClassName = "MovieBuilder", builderMethodName = "movieBuilder")
  public Movie(Long id, String title, int prodYear, String genre, String nation, int runtime, String director,
      String actor) {
    this.id = id;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.director = director;
    this.actor = actor;
  }

  public static MovieBuilder builder(Long id) {
    return movieBuilder().id(id);
  }

}
