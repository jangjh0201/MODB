package com.dodatabase.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PageController.class)
public class PageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(username = "user", roles = "USER")
  public void homeTest() throws Exception {
    mockMvc.perform(get("/v1/home"))
        .andExpect(status().isOk())
        .andExpect(view().name("html/index"));
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  public void movieListTest() throws Exception {
    mockMvc.perform(get("/v1/movielist"))
        .andExpect(status().isOk())
        .andExpect(view().name("html/movie/list"));
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  public void wishListTest() throws Exception {
    mockMvc.perform(get("/v1/wishlist"))
        .andExpect(status().isOk())
        .andExpect(view().name("html/wish/list"));
  }
}
