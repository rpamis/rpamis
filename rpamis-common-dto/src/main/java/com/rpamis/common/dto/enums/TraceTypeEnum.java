package com.rpamis.common.dto.enums;

/**
 * Trace类型枚举
 *
 * @author benym
 * @date 2022/7/8 4:18 下午
 */
public enum TraceTypeEnum {

    /**
     * rpamis默认实现
     */
    RPAMIS("rpamis","rpamis默认实现"),
    /**
     * skywalking实现
     */
    SKYWALK("skywalking","skywalking实现");

    private String type;
    private String desc;

    TraceTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
