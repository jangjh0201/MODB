package com.dodatabase.demo.domain.movie;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieResponse {
  private String title;
  private int prodYear;
  private String genre;
  private String nation;
  private int runtime;
  private String director;
  private String actor;
}
