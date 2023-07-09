package com.rpamis.architecture.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库信息
 *
 * @author benym
 * @date 2022/7/27 3:36 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Database {

  /**
   * enabled
   */
  private Boolean enabled;

  /**
   * host
   */
  private String host;

  /**
   * port
   */
  private String port;

  /**
   * 数据库名称
   */
  private String databaseName;

  /**
   * 用户名
   */
  private String userName;

  /**
   * passWord
   */
  private String passWord;

  /**
   * crud
   */
  private Boolean crud;
}
