package com.benym.rpas.architecture.pojo;

/**
 * @author benym
 * @date 2022/7/26 7:03 下午
 */
public class Consul {

    private String host;

    private String port;

    private Boolean enabled;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
