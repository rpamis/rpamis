package com.rpamis.architecture.service;

import com.rpamis.architecture.config.BaseProjectConfig;
import com.rpamis.architecture.pojo.FileVO;

import java.io.File;

/**
 * 生成类Service
 *
 * @author benym
 * @date 2022/7/20 4:36 下午
 */
public interface BuildService {

  /**
   * 根据入参选择模版进行项目生成
   *
   * @param baseProjectConfig baseProjectConfig
   * @return FileVO
   */
  FileVO architectureBuild(BaseProjectConfig baseProjectConfig);

  /**
   * 根据唯一id下载项目
   *
   * @param id id
   */
  void download(String id);

  /**
   * 根据模版文件和入参，生成项目模版
   *
   * @param file              file
   * @param templatesFtl      templatesFtl
   * @param baseProjectConfig baseProjectConfig
   */
  void generate(File file, String templatesFtl, BaseProjectConfig baseProjectConfig);

  /**
   * 压缩项目，并删除临时文件
   *
   * @param artifactId artifactId
   * @param buildId    buildId
   * @return String
   */
  String zipProject(String artifactId, String buildId);

}
