package com.rpamis.architecture.pojo;

/**
 * SpringBoot版本枚举类
 *
 * @author benym
 * @since 2026/3/6 15:11
 */
public enum SpringBootVersion {

	V1("2.7.10", "SpringBoot2"),

	V2("3.4.0", "SpringBoot3");

	private final String code;

	private final String desc;

	SpringBootVersion(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
