package com.dodatabase.demo.domain.movie;

import java.time.Year;
import jakarta.persistence.Entity; // 원래 Long id로 자동생성 전략을 채택하였는데 String타입의 docId를 받아서 사용하기로 바꾼 후 문제가 생김. 자세히 탐구해볼 것.
import jakarta.persistence.Id;
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
  private Year prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;

}
