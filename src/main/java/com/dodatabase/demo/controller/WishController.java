package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.wish.WishRequest;
import com.dodatabase.demo.domain.wish.WishResponse;
import com.dodatabase.demo.service.WishService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/wish")
public class WishController {

  private final WishService wishService;

  @GetMapping("")
  public String wishList(Model model) {
    List<WishResponse> wishResponses = wishService.findWishes();
    model.addAttribute("wishes", wishResponses);
    return "html/wish/list";
  }

  @PostMapping("")
  @ResponseBody
  public ResponseEntity<Void> createWish(@RequestBody WishRequest wishRequest) {
    if (wishService.findById(wishRequest.getId()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    wishService.create(wishRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public ResponseEntity<Void> removeWish(@PathVariable String id) {
    try {
      if (!wishService.findById(id).isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // ID가 존재하지 않는 경우
      }
      wishService.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 서버 내부 오류 처리
    }
  }

}
