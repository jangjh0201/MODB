package com.dodatabase.demo.domain.wish;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wish {

  @Id
  private String id;

  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;
  private WishDetail wishDetail;

  @Builder(builderClassName = "Wishbuilder", builderMethodName = "wishbuilder")
  public Wish(String id, String title, int prodYear, String genre,
      String nation, int runtime, String director, String actor, WishDetail wishDetail) {
    this.id = id;
    this.title = title;
    this.prodYear = prodYear;
    this.genre = genre;
    this.nation = nation;
    this.runtime = runtime;
    this.director = director;
    this.actor = actor;
    this.wishDetail = wishDetail;
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
