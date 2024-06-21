package com.dodatabase.demo.controller;

import com.dodatabase.demo.domain.wishlist.WishRequest;
import com.dodatabase.demo.domain.wishlist.WishResponse;
import com.dodatabase.demo.service.FavoriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/favorites")
public class FavoriteController {

  private final FavoriteService favoriteService;

  @GetMapping("")
  public String list(Model model) {
    List<WishResponse> wishResponses = favoriteService.findMovies();
    model.addAttribute("movies", wishResponses);
    return "html/favorite/list";
  }

  @PostMapping("")
  @ResponseBody
  public ResponseEntity<Void> createMovie(@RequestBody WishRequest wishRequest) {
    if (favoriteService.findById(wishRequest.getId()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    favoriteService.create(wishRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("")
  @ResponseBody
  public ResponseEntity<Void> removeMovie(@RequestBody String id) {
    favoriteService.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
