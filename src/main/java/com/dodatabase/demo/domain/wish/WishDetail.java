package com.dodatabase.demo.domain.wish;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WishDetail {
  private List<String> posters;
  private String plot;
}
