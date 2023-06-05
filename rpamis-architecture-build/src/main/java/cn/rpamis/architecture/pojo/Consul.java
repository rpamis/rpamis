package cn.rpamis.architecture.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * Consul注册中心
 *
 * @author benym
 * @date 2022/7/26 7:03 下午
 */
@Data
@Builder
public class Consul {

    /**
     * host
     */
    private String host;

    /**
     * port
     */
    private String port;

    /**
     * enabled
     */
    private Boolean enabled;
}
