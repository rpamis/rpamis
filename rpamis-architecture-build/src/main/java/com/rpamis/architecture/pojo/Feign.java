package com.rpamis.architecture.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OpenFegin信息
 *
 * @author benym
 * @date 2022/7/26 7:02 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feign implements Serializable {

  private static final long serialVersionUID = 931400845255790384L;
  /**
   * enabled
   */
  private Boolean enabled;
}
