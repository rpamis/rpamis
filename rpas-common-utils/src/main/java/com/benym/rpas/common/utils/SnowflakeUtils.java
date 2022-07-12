package com.benym.rpas.common.utils;

import cn.hutool.core.lang.generator.SnowflakeGenerator;

/**
 * @Time : 2022/7/10 21:16
 */
public class SnowflakeUtils {

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
