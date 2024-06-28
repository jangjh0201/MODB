package com.dodatabase.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
public class PageController {

  @GetMapping("/home")
  public String home() {
    return "html/index";
  }

  @GetMapping("/movielist")
  public String movieList() {
    return "html/movie/list";
  }

  @GetMapping("/wishlist")
  public String wishList() {
    return "html/wish/list";
  }
}
