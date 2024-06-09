package com.dodatabase.demo.domain.movie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue; // 원래 Long id로 자동생성 전략을 채택하였는데
import javax.persistence.GenerationType; // String타입의 docId를 받아서 사용하기로 바꾼 후 문제가 생김. 자세히 탐구해볼 것.
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
  private String id;

  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;

  @Builder(builderClassName = "MovieBuilder", builderMethodName = "movieBuilder")
  public Movie(String id, String title, int prodYear, String genre,
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

  public static MovieBuilder builder(String id) {
    return movieBuilder().id(id);
  }

}
