package com.rpamis.architecture.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Consul注册中心
 *
 * @author benym
 * @date 2022/7/26 7:03 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Consul implements Serializable {

  private static final long serialVersionUID = 2541572673289440848L;
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
