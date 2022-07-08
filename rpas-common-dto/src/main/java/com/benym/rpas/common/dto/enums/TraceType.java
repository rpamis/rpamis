package com.benym.rpas.common.dto.enums;

/**
 * @date 2022/7/8 4:18 下午
 */
public enum TraceType {
    RPAS("rpas","rpas默认实现"),
    SKYWALK("skywalking","skywaling实现");

    private String type;
    private String desc;

    TraceType(String type, String desc) {
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
