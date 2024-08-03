package com.dodatabase.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(username = "user", roles = "USER")
  public void testLogin() throws Exception {
    mockMvc.perform(get("/v1/login"))
        .andExpect(status().isOk())
        .andExpect(view().name("html/auth/login"));
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  public void testLoginFail() throws Exception {
    mockMvc.perform(get("/v1/login").param("failure", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("html/auth/login"))
        .andExpect(model().attributeExists("failure"))
        .andExpect(model().attribute("failure", "로그인에 실패했습니다."));
  }
}
