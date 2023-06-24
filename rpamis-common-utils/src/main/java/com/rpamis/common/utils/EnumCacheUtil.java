package com.rpamis.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举缓存工具类，powered by chatgpt
 *
 * @author benym
 * @date 2023/6/12 11:31
 */
public class EnumCacheUtil {

    private EnumCacheUtil() {
        throw new IllegalStateException("工具类禁止实例化");
    }

    /**
     * code缓存
     */
    private static final Map<Class<? extends CachableEnum<?,?>>, Map<Object, CachableEnum<?,?>>> CODE_CACHE = new ConcurrentHashMap<>();

    /**
     * desc缓存
     */
    private static final Map<Class<? extends CachableEnum<?,?>>, Map<Object, CachableEnum<?,?>>> DESC_CACHE = new ConcurrentHashMap<>();

    static {
        cacheEnums();
    }

    /**
     * 通过枚举class和code获取枚举
     *
     * @param enumClass 枚举class
     * @param code      code
     * @return {@link T}
     */
    public static <T extends CachableEnum<?,?>> T getEnumByCode(Class<T> enumClass, Object code) {
        return enumClass.cast(CODE_CACHE.getOrDefault(enumClass, new HashMap<>(8)).get(code));
    }

    /**
     * 通过枚举class和desc获取枚举
     *
     * @param enumClass 枚举class
     * @param desc      desc
     * @return {@link T}
     */
    public static <T extends CachableEnum<?,?>> T getEnumByDesc(Class<T> enumClass, Object desc) {
        return enumClass.cast(DESC_CACHE.getOrDefault(enumClass, new HashMap<>(8)).get(desc));
    }

    /**
     * 缓存枚举方法
     */
    private static void cacheEnums() {
        List<Class<? extends CachableEnum<?,?>>> implClasses = ClassScannerUtil.getCachableEnumImplClasses();
        for (Class<? extends CachableEnum<?,?>> implClass: implClasses) {
            cacheEnumClass(implClass);
        }
    }

    /**
     * 缓存枚举到Cache Map
     *
     * @param implClass 实现CachableEnum接口的实现类
     */
    private static void cacheEnumClass(Class<? extends CachableEnum<?,?>> implClass) {
        Map<Object, CachableEnum<?,?>> codeMap = new HashMap<>(8);
        Map<Object, CachableEnum<?,?>> descMap = new HashMap<>(8);
        CachableEnum<?,?>[] enumConstants = implClass.getEnumConstants();
        for (CachableEnum<?,?> cachableEnum : enumConstants) {
            Object code = cachableEnum.getCode();
            Object desc = cachableEnum.getDesc();
            codeMap.put(code, cachableEnum);
            descMap.put(desc, cachableEnum);
        }
        CODE_CACHE.put(implClass, codeMap);
        DESC_CACHE.put(implClass, descMap);
    }

}