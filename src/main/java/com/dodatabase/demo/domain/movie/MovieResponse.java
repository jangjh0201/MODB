package com.dodatabase.demo.domain.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MovieResponse {
  private String query;
  private String kmaQuery;
  private long totalCount;
  private List<Data> data;

  @Builder
  private MovieResponse(String query, String kmaQuery, long totalCount, List<Data> data) {
    this.query = query;
    this.kmaQuery = kmaQuery;
    this.totalCount = totalCount;
    this.data = data;
  }

  @Getter
  @NoArgsConstructor
  public static class Data {
    private String collName;
    private long totalCount;
    private long count;
    private List<Result> result;

    @Builder
    private Data(String collName, long totalCount, long count, List<Result> result) {
      this.collName = collName;
      this.totalCount = totalCount;
      this.count = count;
      this.result = result;
    }

    @Getter
    @NoArgsConstructor
    public static class Result {
      @JsonProperty("DOCID")
      private String docId;
      private String title;
      private String prodYear;
      private String genre;
      private String nation;
      private String runtime;
      private Directors directors;
      private Actors actors;

      @Builder
      private Result(String docId, String title, String prodYear, String genre,
          String nation, String runtime, Directors directors, Actors actors) {
        this.docId = docId;
        this.title = title;
        this.prodYear = prodYear;
        this.genre = genre;
        this.nation = nation;
        this.runtime = runtime;
        this.directors = directors;
        this.actors = actors;
      }

      @Getter
      @NoArgsConstructor
      public static class Directors {
        @JsonProperty("director")
        private List<Director> directors;

        @Builder
        private Directors(List<Director> directors) {
          this.directors = directors;
        }

        @Getter
        @NoArgsConstructor
        public static class Director {
          private String directorNm;
          private String directorEnNm;
          private String directorId;

          @Builder
          private Director(String directorNm, String directorEnNm, String directorId) {
            this.directorNm = directorNm;
            this.directorEnNm = directorEnNm;
            this.directorId = directorId;
          }
        }
      }

      @Getter
      @NoArgsConstructor
      public static class Actors {
        @JsonProperty("actor")
        private List<Actor> actors;

        @Builder
        public Actors(List<Actor> actors) {
          this.actors = actors;
        }

        @Getter
        @NoArgsConstructor
        public static class Actor {
          private String actorNm;
          private String actorEnNm;
          private String actorId;

          @Builder
          public Actor(String actorNm, String actorEnNm, String actorId) {
            this.actorNm = actorNm;
            this.actorEnNm = actorEnNm;
            this.actorId = actorId;
          }
        }
      }
    }
  }
}
