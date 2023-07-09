package com.rpamis.architecture.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目文件VO
 *
 * @author benym
 * @date 2022/7/20 4:59 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileVO implements Serializable {

  private static final long serialVersionUID = -1011756219112553426L;
  /**
   * id
   */
  private String id;

  /**
   * 文件路径
   */
  private String filePath;
}
