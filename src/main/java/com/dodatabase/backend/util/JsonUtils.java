package com.dodatabase.backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static byte[] serialize(Object obj) throws Exception {
    return objectMapper.writeValueAsBytes(obj);
  }

  public static <T> T deserialize(byte[] data, TypeReference<T> typeReference) throws Exception {
    return objectMapper.readValue(data, typeReference);
  }
}
