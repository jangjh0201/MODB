package com.dodatabase.backend.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodatabase.backend.key.ApiKey;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.reactive.function.client.WebClient;

@SpringJUnitConfig(WebClientConfig.class) // WebClientConfig만 로드
public class WebClientConfigTest {

  @Autowired
  private WebClient webClient;

  @MockBean
  private ApiKey apiKey; // 필요한 경우 mock으로 설정

  @Test
  public void testWebClientWithMockApiKey() {
    // ApiKey mock 설정
    Mockito.when(apiKey.getKey()).thenReturn("key");

    // WebClient가 잘 설정되었는지 확인
    assertNotNull(webClient);
  }
}
