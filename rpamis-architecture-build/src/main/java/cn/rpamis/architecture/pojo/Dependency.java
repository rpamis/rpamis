package cn.rpamis.architecture.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * 依赖信息
 *
 * @author benym
 * @date 2022/7/26 7:00 下午
 */
@Data
@Builder
public class Dependency {

    /**
     * consul
     */
    private Consul consul;

    /**
     * feign
     */
    private Feign feign;

    /**
     * database
     */
    private Database database;
}
