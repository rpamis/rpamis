package com.benym.rpamis.common.exception.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 全局异常Properties
 *
 * @author benym
 * @date 2023/2/15 21:40
 */
@ConfigurationProperties(ExceptionProperties.PREFIX)
public class ExceptionProperties {

    /**
     * 前缀
     */
    public static final String PREFIX = "rpamis.exception";

    /**
     * 是否开启全局Web异常
     */
    private String enable = "true";

    /**
     * 是否开启全局RPC统一返回体
     */
    private Boolean rpcPack = true;

    /**
     * 是否开启Exception.class捕获
     */
    private Boolean includeExceptionClass = true;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public Boolean getRpcPack() {
        return rpcPack;
    }

    public void setRpcPack(Boolean rpcPack) {
        this.rpcPack = rpcPack;
    }

    public Boolean getIncludeExceptionClass() {
        return includeExceptionClass;
    }

    public void setIncludeExceptionClass(Boolean includeExceptionClass) {
        this.includeExceptionClass = includeExceptionClass;
    }
}
