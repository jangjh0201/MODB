package com.dodatabase.demo.key;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "movie-api")
@ConstructorBinding
public class ApiKey {

  @NotBlank
  private final String key;

  @Builder
  public ApiKey(String key) {
    this.key = key;
  }

}
