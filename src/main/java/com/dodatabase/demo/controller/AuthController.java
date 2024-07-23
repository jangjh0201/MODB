package com.dodatabase.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
public class AuthController {
  @GetMapping("/login")
  public String login() {
    return "html/login/form";
  }

  @GetMapping(value = "/login", params = "failure")
  public String loginFail(Model model) {
    model.addAttribute("failure", "로그인에 실패했습니다.");
    return "html/login/form";
  }
}
