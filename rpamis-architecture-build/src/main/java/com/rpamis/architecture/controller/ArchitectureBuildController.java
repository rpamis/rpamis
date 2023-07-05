package com.rpamis.architecture.controller;

import com.rpamis.architecture.config.BaseProjectConfig;
import com.rpamis.architecture.pojo.FileVO;
import com.rpamis.architecture.service.BuildService;
import com.rpamis.common.dto.response.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 起始Controller
 *
 * @author benym
 * @date 2022/7/20 4:47 下午
 */
@RestController
@RequestMapping("/rpamis")
public class ArchitectureBuildController {

  @Autowired
  private BuildService buildService;

  @ApiOperation("项目生成")
  @PostMapping("/build")
  public Response<FileVO> build(@RequestBody @Validated BaseProjectConfig baseProjectConfig) {
    FileVO fileVO = buildService.architectureBuild(baseProjectConfig);
    return Response.success(fileVO);
  }

  @ApiOperation("下载项目")
  @GetMapping("/download/{id}")
  public void download(@PathVariable("id") String id) {
    buildService.download(id);
  }

}
