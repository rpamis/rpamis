package com.benym.rpas.architecture.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Time : 2022/7/28 21:53
 */
public class StringUtils {

    private static final Pattern SHORT_LINE_PATTERN = Pattern.compile("-([a-z])");


    public static String getMainName (String str){
        return captureName(toHump(str));
    }

    // -线分割，之后一个字母转大写
    public static String toHump (String str){
        //正则匹配下划线及后一个字符，删除下划线并将匹配的字符转成大写
        Matcher matcher = SHORT_LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配的子串替换成指定字符串，并且将替换后的子串及之前到上次匹配的子串之后的字符串添加到StringBuffer对象中
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            //把之后的字符串也添加到StringBuffer对象中
            matcher.appendTail(sb);
        } else {
            //去除除字母之外的前面带的下划线
            return sb.toString().replaceAll("-", "");
        }
        return toHump(sb.toString());
    }

    // 首字符转大写
    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
