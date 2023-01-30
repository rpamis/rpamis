package com.benym.rpas.usage.test.utils.converter;

import net.sf.cglib.core.Converter;

/**
 * @author benym
 * @date 2022/11/23 21:14
 */
public class DiffConverter implements Converter {

    // source 源对象属性，target 目标对象属性类，context 目标对象setter方法名
    @Override
    public Object convert(Object source, Class target, Object context) {
        if (source instanceof String) {
            return Integer.parseInt((String) source);
        } else if (source instanceof Integer) {
            return source.toString();
        }
        return source;
    }
}
