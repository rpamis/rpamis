package com.rpamis.common.utils;

import com.rpamis.common.dto.exception.ExceptionFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Jackson工具类
 *
 * @author benym
 * @date 2022/8/23 10:40
 */
public class JackSonUtils {

    private JackSonUtils() {
        throw new IllegalStateException("工具类，禁止实例化");
    }

    private static volatile ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            synchronized (ObjectMapper.class) {
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
