package com.dodatabase.demo.service;

import com.dodatabase.demo.domain.wish.Wish;
import com.dodatabase.demo.domain.wish.WishDetail;
import com.dodatabase.demo.domain.wish.WishRequest;
import com.dodatabase.demo.domain.wish.WishResponse;
import com.dodatabase.demo.repository.WishRepository;
import com.dodatabase.demo.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishService {

  private final WishRepository wishRepository;

  @Transactional
  public void create(WishRequest wishRequest) {
    try {
      Wish wish = convertToWish(wishRequest);
      wishRepository.save(wish);
    } catch (Exception e) {
      e.printStackTrace();
      // 예외 처리 추가
    }
  }

  @Transactional(readOnly = true)
  public List<WishResponse> findWishes() {
    List<Wish> wishes = wishRepository.findAll();
    return wishes.stream()
        .map(this::convertToWishResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<WishResponse> findById(String id) {
    Optional<Wish> wish = wishRepository.findById(id);
    return wish.map(this::convertToWishResponse);
  }

  @Transactional
  public void deleteById(String id) {
    wishRepository.deleteById(id);
  }

  private Wish convertToWish(WishRequest wishRequest) throws Exception {
    byte[] postersData = JsonUtils.serialize(wishRequest.getDetail().getPosters());
    return Wish.builder(wishRequest.getId())
        .title(wishRequest.getTitle())
        .prodYear(wishRequest.getProdYear())
        .genre(wishRequest.getGenre())
        .nation(wishRequest.getNation())
        .runtime(wishRequest.getRuntime())
        .director(wishRequest.getDirector())
        .actor(wishRequest.getActor())
        .posters(postersData)
        .plot(wishRequest.getDetail().getPlot())
        .build();
  }

  private WishResponse convertToWishResponse(Wish wish) {
    List<String> posters = null;
    try {
      posters = JsonUtils.deserialize(wish.getPosters(), new TypeReference<List<String>>() {
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return WishResponse.builder()
        .id(wish.getId())
        .title(wish.getTitle())
        .prodYear(wish.getProdYear())
        .genre(wish.getGenre())
        .nation(wish.getNation())
        .runtime(wish.getRuntime())
        .director(wish.getDirector())
        .actor(wish.getActor())
        .detail(WishDetail.builder()
            .posters(posters)
            .plot(wish.getPlot())
            .build())
        .build();
  }
}
