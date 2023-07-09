package com.rpamis.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.rpamis.exception.dto.ExceptionFactory;
import java.util.Map;

/**
 * Jackson工具类
 *
 * @author benym
 * @date 2022/8/23 10:40
 */
public class JackSonUtil {

  private JackSonUtil() {
    throw new IllegalStateException("工具类，禁止实例化");
  }

  private static volatile ObjectMapper objectMapper;

  public static ObjectMapper getObjectMapper() {
    if (objectMapper == null) {
      synchronized (JackSonUtil.class) {
        if (objectMapper == null) {
          objectMapper = new ObjectMapper();
        }
      }
    }
    return objectMapper;
  }

  public static Map<Object, Object> toMap(String str) {
    try {
      return getObjectMapper().readValue(str, new TypeReference<Map<Object, Object>>() {
      });
    } catch (Exception e) {
      throw ExceptionFactory.sysException("JackSonUtils str toMap error", e);
    }
  }
}
