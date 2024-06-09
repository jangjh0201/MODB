package com.dodatabase.demo.key;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("movie-api")
public class ApiKey {

  @NotBlank
  private final String key;

  @Builder
  public ApiKey(String key) {
    this.key = key;
  }

}
