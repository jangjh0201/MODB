package com.dodatabase.demo.domain.wish;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wish")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wish {

  @Id
  @Column(name = "id", columnDefinition = "VARCHAR(6)", nullable = false, unique = true)
  private String id;

  @Column(name = "title", columnDefinition = "VARCHAR(255)", nullable = false)
  private String title;

  @Column(name = "production_year", columnDefinition = "INTEGER")
  private int prodYear;

  @Column(name = "genre", columnDefinition = "VARCHAR(255)")
  private String genre;

  @Column(name = "nation", columnDefinition = "VARCHAR(255)")
  private String nation;

  @Column(name = "running_time", columnDefinition = "INTEGER")
  private int runtime;

  @Column(name = "director", columnDefinition = "VARCHAR(255)")
  private String director;

  @Column(name = "actor", columnDefinition = "VARCHAR(500)")
  private String actor;

  @Column(name = "posters", columnDefinition = "VARBINARY(1024)")
  private byte[] posters;

  @Column(name = "plot", columnDefinition = "VARCHAR(500)")
  private String plot;

  @Builder(builderClassName = "Wishbuilder", builderMethodName = "wishbuilder")
  public Wish(String id, String title, int prodYear, String genre, String nation,
      int runtime, String director, String actor, byte[] posters, String plot) {
    this.id = id;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.director = director;
    this.actor = actor;
    this.posters = posters;
    this.plot = plot;
  }

  public static Wishbuilder builder(String id) {
    return wishbuilder().id(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Wish movie = (Wish) o;
    return Objects.equals(id, movie.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
