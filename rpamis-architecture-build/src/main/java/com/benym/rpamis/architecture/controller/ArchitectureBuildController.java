package com.benym.rpamis.architecture.controller;

import com.benym.rpamis.architecture.service.BuildService;
import com.benym.rpamis.architecture.config.BaseProjectConfig;
import com.benym.rpamis.architecture.pojo.FileVO;
import com.benym.rpamis.common.dto.response.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author benym
 * @date 2022/7/20 4:47 下午
 */
@RestController
@RequestMapping("/rpas")
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
