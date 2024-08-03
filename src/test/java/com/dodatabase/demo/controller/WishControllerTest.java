package com.dodatabase.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodatabase.demo.domain.wish.WishDetail;
import com.dodatabase.demo.domain.wish.WishRequest;
import com.dodatabase.demo.domain.wish.WishResponse;
import com.dodatabase.demo.service.WishService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WishController.class)
public class WishControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WishService wishService;

  @MockBean
  private ModelMapper modelMapper;

  private ObjectMapper objectMapper = new ObjectMapper();

  private WishRequest wishRequest;
  private WishResponse wishResponse;
  private List<WishResponse> wishResponseList;

  @BeforeEach
  void initialize() {
    wishRequest = WishRequest.builder()
        .id("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .detail(WishDetail.builder()
            .posters(Arrays.asList("http://file.koreafilm.or.kr/thm/02/00/02/63/tn_DPF006410.JPG"))
            .plot("클론 전쟁이 시작되었던 때로부터 3년이 지나고...")
            .build())
        .build();

    wishResponse = WishResponse.builder()
        .id("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .detail(WishDetail.builder()
            .posters(Arrays.asList("http://file.koreafilm.or.kr/thm/02/00/02/63/tn_DPF006410.JPG"))
            .plot("클론 전쟁이 시작되었던 때로부터 3년이 지나고...")
            .build())
        .build();

    wishResponseList = Collections.singletonList(wishResponse);
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  void wishListTest() throws Exception {
    when(wishService.findWishes()).thenReturn(wishResponseList);

    mockMvc.perform(get("/v1/wish"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("F10538"))
        .andExpect(jsonPath("$[0].title").value("스타워즈 에피소드 3 : 시스의 복수"))
        .andExpect(jsonPath("$[0].prodYear").value(2005))
        .andExpect(jsonPath("$[0].genre").value("액션,SF,어드벤처,판타지"))
        .andExpect(jsonPath("$[0].nation").value("미국"))
        .andExpect(jsonPath("$[0].runtime").value(139))
        .andExpect(jsonPath("$[0].director").value("조지 루카스"))
        .andExpect(jsonPath("$[0].actor").value("이완 맥그리거"))
        .andExpect(
            jsonPath("$[0].detail.posters[0]").value("http://file.koreafilm.or.kr/thm/02/00/02/63/tn_DPF006410.JPG"))
        .andExpect(jsonPath("$[0].detail.plot").value("클론 전쟁이 시작되었던 때로부터 3년이 지나고..."))
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  void createWishTest_Success() throws Exception {
    when(wishService.findById(any())).thenReturn(Optional.empty());

    mockMvc.perform(post("/v1/wish")
        .with(csrf())
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(wishRequest)))
        .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  void createWishTest_AlreadyExists() throws Exception {
    when(wishService.findById("F10538")).thenReturn(Optional.of(wishResponse));

    mockMvc.perform(post("/v1/wish")
        .with(csrf())
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(wishRequest)))
        .andExpect(status().isConflict());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  void removeWishTest_Success() throws Exception {
    when(wishService.findById("F10538")).thenReturn(Optional.of(wishResponse));

    mockMvc.perform(delete("/v1/wish/F10538")
        .with(csrf())
        .contentType("application/json"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  void removeWishTest_NotFound() throws Exception {
    when(wishService.findById("F10538")).thenReturn(Optional.empty());

    mockMvc.perform(delete("/v1/wish/F10538")
        .with(csrf())
        .contentType("application/json"))
        .andExpect(status().isNotFound());
  }
}
