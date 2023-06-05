package com.rpamis.common.utils;

import cn.hutool.core.lang.generator.SnowflakeGenerator;

/**
 * Snowflake工具类
 *
 * @author benym
 * @date 2022/7/10 21:16
 */
public class SnowflakeUtils {

    private SnowflakeUtils() {
        throw new IllegalStateException("工具类，禁止实例化");
    }

    private static volatile SnowflakeGenerator snowflakeGenerator;

    public static SnowflakeGenerator get() {
        if (snowflakeGenerator == null) {
            synchronized (SnowflakeGenerator.class) {
                if (snowflakeGenerator == null) {
                    snowflakeGenerator = new SnowflakeGenerator();
                }
            }
        }
        return snowflakeGenerator;
    }
}
