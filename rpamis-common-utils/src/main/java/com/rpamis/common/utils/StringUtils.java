package com.rpamis.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类，提供常用的字符串处理方法
 *
 * @author benym
 * @date 2024/06/07
 */
public class StringUtils {

	private static final Pattern SHORT_LINE_PATTERN = Pattern.compile("-([a-z])");

	/**
	 * 私有构造函数，禁止实例化
	 */
	private StringUtils() {
		throw new IllegalStateException("工具类，禁止实例化");
	}

	/**
	 * 检查字符串是否为空或长度为0
	 * @param str 要检查的字符串
	 * @return 如果字符串为空或长度为0，返回true；否则返回false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 检查字符串是否为非空且长度大于0
	 * @param str 要检查的字符串
	 * @return 如果字符串非空且长度大于0，返回true；否则返回false
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 检查字符串是否为空或仅包含空白字符
	 * @param str 要检查的字符串
	 * @return 如果字符串为空或仅包含空白字符，返回true；否则返回false
	 */
	public static boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查字符串是否为非空且至少包含一个非空白字符
	 * @param str 要检查的字符串
	 * @return 如果字符串非空且至少包含一个非空白字符，返回true；否则返回false
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 将短横线分隔的字符串转换为驼峰命名
	 * @param str 要转换的字符串
	 * @return 驼峰命名的字符串
	 */
	public static String toHump(String str) {
		if (isEmpty(str)) {
			return str;
		}
		// 正则匹配下划线及后一个字符，删除下划线并将匹配的字符转成大写
		Matcher matcher = SHORT_LINE_PATTERN.matcher(str);
		StringBuilder sb = new StringBuilder(str);
		if (matcher.find()) {
			sb = new StringBuilder();
			// 将当前匹配的子串替换成指定字符串，并且将替换后的子串及之前到上次匹配的子串之后的字符串添加到StringBuffer对象中
			// 正则之前的字符和被替换的字符
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
			// 把之后的字符串也添加到StringBuffer对象中
			matcher.appendTail(sb);
		}
		else {
			// 去除除字母之外的前面带的下划线
			return sb.toString().replaceAll("-", "");
		}
		return toHump(sb.toString());
	}

	/**
	 * 首字符转大写
	 * @param str 要转换的字符串
	 * @return 首字符大写的字符串
	 */
	public static String captureName(String str) {
		if (isEmpty(str)) {
			return str;
		}
		// 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
		char[] cs = str.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	/**
	 * 获取主类名
	 * @param str str
	 * @return String
	 */
	public static String getMainName(String str) {
		return captureName(toHump(str));
	}

}