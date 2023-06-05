package cn.rpamis.architecture.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * OpenFegin信息
 *
 * @author benym
 * @date 2022/7/26 7:02 下午
 */
@Data
@Builder
public class Feign {

    /**
     * enabled
     */
    private Boolean enabled;
}
