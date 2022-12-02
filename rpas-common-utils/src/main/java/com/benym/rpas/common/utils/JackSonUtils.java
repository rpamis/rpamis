package com.benym.rpas.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Time : 2022/8/23 10:40
 */
public class JackSonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JackSonUtils.class);

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
            logger.error("utils,str转map失败,{}", e.getMessage());
            throw new RuntimeException("JackSonUtils toMap error", e);
        }
    }
}
