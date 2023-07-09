package com.rpamis.architecture.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 依赖信息
 *
 * @author benym
 * @date 2022/7/26 7:00 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dependency implements Serializable {

  private static final long serialVersionUID = 2002865978882205674L;
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
