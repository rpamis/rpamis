package com.rpamis.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpamis.common.dto.exception.ExceptionFactory;
import com.rpamis.common.dto.response.PageResponse;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Rpamis Bean拷贝工具类
 *
 * @author benym
 * @date 2022/7/7 21:56
 */
public class RpamisBeanUtils {

    /**
     * 享元模式
     */
    private static final Map<String, BeanCopier> BENCOPIER_MAP = new ConcurrentReferenceHashMap<>();

    private RpamisBeanUtils() {
        throw new IllegalStateException("工具类，禁止实例化");
    }

    /**
     * 无converter模式下获取BeanCopier实例
     *
     * @param sourceClass 源类
     * @param targetClass 目标类
     * @return BeanCopier
     */
    private static <S, T> BeanCopier getBeanCopier(Class<S> sourceClass, Class<T> targetClass) {
        BeanCopier beanCopier;
        String beanKeyStr = String.valueOf(sourceClass) + targetClass;
        if (!BENCOPIER_MAP.containsKey(beanKeyStr)) {
            synchronized (RpamisBeanUtils.class) {
                if (!BENCOPIER_MAP.containsKey(beanKeyStr)) {
                    beanCopier = BeanCopier.create(sourceClass, targetClass, false);
                    BENCOPIER_MAP.put(beanKeyStr, beanCopier);
                } else {
                    beanCopier = BENCOPIER_MAP.get(beanKeyStr);
                }
            }
        } else {
            beanCopier = BENCOPIER_MAP.get(beanKeyStr);
        }
        return beanCopier;
    }

    /**
     * converter模式下获取BeanCopier实例
     *
     * @param sourceClass 源类
     * @param targetClass 目标类
     * @return BeanCopier
     */
    private static <S, T> BeanCopier getBeanCopier(Class<S> sourceClass, Class<T> targetClass, Converter converter) {
        BeanCopier beanCopier;
        String beanKeyStr = String.valueOf(sourceClass) + targetClass + converter.toString();
        if (!BENCOPIER_MAP.containsKey(beanKeyStr)) {
            synchronized (RpamisBeanUtils.class) {
                if (!BENCOPIER_MAP.containsKey(beanKeyStr)) {
                    beanCopier = BeanCopier.create(sourceClass, targetClass, true);
                    BENCOPIER_MAP.put(beanKeyStr, beanCopier);
                } else {
                    beanCopier = BENCOPIER_MAP.get(beanKeyStr);
                }
            }
        } else {
            beanCopier = BENCOPIER_MAP.get(beanKeyStr);
        }
        return beanCopier;
    }

    /**
     * 拷贝source对象属性到target中，不使用converter，名字和类型不严格匹配时将不拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copy(Object source, Object target) {
        BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
    }

    /**
     * 拷贝source对象属性到target class中，不使用converter，名字和类型不严格匹配时将不拷贝
     *
     * @param source 源对象
     * @param clazz  目标类
     * @return copy后的对象
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T target;
        try {
            target = clazz.newInstance();
            copy(source, target);
        } catch (Exception e) {
            throw ExceptionFactory.sysException("RpasBeanUtils copy exception:", e);
        }
        return target;
    }

    /**
     * 拷贝source对象属性到target中，使用自定义converter，拷贝规则严格符合converter规则
     *
     * @param source    源对象
     * @param target    目标对象
     * @param converter 自定义converter
     */
    public static void copy(Object source, Object target, Converter converter) {
        BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass(), converter);
        beanCopier.copy(source, target, converter);
    }

    /**
     * 拷贝source对象属性到target class中，使用自定义converter，拷贝规则严格符合converter规则
     *
     * @param source    源对象
     * @param clazz     目标类
     * @param converter 自定义converter
     * @return copy后的对象
     */
    public static <T> T copy(Object source, Class<T> clazz, Converter converter) {
        if (source == null) {
            return null;
        }
        T target;
        try {
            target = clazz.newInstance();
            copy(source, target, converter);
        } catch (Exception e) {
            throw ExceptionFactory.sysException("RpasBeanUtils copy with converter exception:", e);
        }
        return target;
    }

    /**
     * 拷贝源list对象到新class list
     *
     * @param sources 源对象List
     * @param clazz   目标类
     * @return 拷贝后的新class list
     */
    public static <T> List<T> copyToList(List<?> sources, Class<T> clazz) {
        if (sources == null) {
            return Collections.emptyList();
        }
        List<T> targets = new ArrayList<>(sources.size());
        if (!sources.isEmpty()) {
            sources.forEach(source -> {
                T clone = copy(source, clazz);
                targets.add(clone);
            });
        }
        return targets;
    }

    /**
     * 拷贝源list对象到新class list，使用自定义converter
     *
     * @param sources   源对象List
     * @param clazz     目标类
     * @param converter 自定义converter
     * @return 拷贝后的新class list
     */
    public static <T> List<T> copyToList(List<?> sources, Class<T> clazz, Converter converter) {
        if (sources == null) {
            return Collections.emptyList();
        }
        List<T> targets = new ArrayList<>(sources.size());
        if (!sources.isEmpty()) {
            sources.forEach(source -> {
                T clone = copy(source, clazz, converter);
                targets.add(clone);
            });
        }
        return targets;
    }

    /**
     * 将page对象转化为PageResponse
     *
     * @param page mybatisPlus page对象
     * @return PageResponse
     */
    public static <T> PageResponse<T> toPageResponse(Page<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPageIndex((int) page.getCurrent());
        pageResponse.setPageSize((int) page.getSize());
        pageResponse.setTotalCount((int) page.getTotal());
        pageResponse.setList(page.getRecords());
        return pageResponse;
    }

    /**
     * 将PageResponse对象，转化内部class
     *
     * @param sourcePageResponse 源PageResponse
     * @param clazz              目标class
     * @return 将PageResponse<class>
     */
    public static <S, T> PageResponse<T> toPageResponse(PageResponse<S> sourcePageResponse, Class<T> clazz) {
        if (sourcePageResponse == null) {
            return null;
        }
        PageResponse<T> pageResponse = copy(sourcePageResponse, PageResponse.class);
        if (!sourcePageResponse.getList().isEmpty()) {
            pageResponse.setList(RpamisBeanUtils.copyToList(sourcePageResponse.getList(), clazz));
        }
        return pageResponse;
    }

    /**
     * 将page对象转化为PageResponse, function converter形式
     *
     * @param page              mybatisPlus page对象
     * @param functionConverter functionConverter
     * @return PageResponse
     */
    public static <S, T> PageResponse<T> toPageResponse(Page<S> page, Function<S, T> functionConverter) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPageIndex((int) page.getCurrent());
        pageResponse.setPageSize((int) page.getSize());
        pageResponse.setTotalCount((int) page.getTotal());
        if (page.getRecords().isEmpty()) {
            pageResponse.setList(new ArrayList<>());
            return pageResponse;
        }
        Collection<T> list = page.getRecords().stream()
                .map(functionConverter)
                .collect(Collectors.toList());
        pageResponse.setList(list);
        return pageResponse;
    }

    /**
     * 将PageResponse对象 转换不同内部class, function converter形式
     *
     * @param sourcePageResponse PageResponse对象
     * @param functionConverter  functionConverter
     * @return PageResponse
     */
    public static <S, T> PageResponse<T> toPageResponse(PageResponse<S> sourcePageResponse, Function<S, T> functionConverter) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPageIndex(sourcePageResponse.getPageIndex());
        pageResponse.setPageSize(sourcePageResponse.getPageSize());
        pageResponse.setTotalCount(sourcePageResponse.getTotalCount());
        if (sourcePageResponse.getList().isEmpty()) {
            pageResponse.setList(new ArrayList<>());
            return pageResponse;
        }
        Collection<T> list = sourcePageResponse.getList().stream()
                .map(functionConverter)
                .collect(Collectors.toList());
        pageResponse.setList(list);
        return pageResponse;
    }

}
