package com.rpamis.architecture.template;

import com.rpamis.architecture.config.BaseProjectConfig;
import com.rpamis.architecture.pojo.FileVO;
import com.rpamis.architecture.service.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象模板方法 如何扩展文件目录？ 答：在AbstractTemplate子类pathMap中添加对应的key(任意),value(文件路径)，即可生成对应路径
 * 如何扩展模版文件？ 答：在AbstractTemplate子类ftlMap中添加和pathMap中相同的key,value为ftl文件名，即可生成对应ftl文件到指定路径
 * 新增ftl文件前缀需和pathMap的key一致 无需修改额外的代码
 *
 * @author benym
 * @date 2022/7/21 10:19 上午
 */
public abstract class AbstractBuildTemplate {

	@Autowired(required = false)
	protected BuildService buildService;

	protected Map<String, String> pathMap = new HashMap<>(64);

	protected MultiValueMap<String, String> ftlMap = new LinkedMultiValueMap<>(64);

	protected Map<String, String> parentDirMap = new HashMap<>(32);

	protected String buildId = "";

	protected BaseProjectConfig projectConfig;

	/**
	 * 获取模版类型
	 * @return String
	 */
	protected abstract String getTemplateType();

	/**
	 * 初始化父级目录map
	 */
	protected abstract void initParentDirMap();

	/**
	 * 初始化项目基础路径
	 */
	protected abstract void initPath();

	/**
	 * 解析模版路径
	 */
	protected abstract void resolve();

	/**
	 * 生成项目骨架和模版
	 * @return FileVO
	 */
	protected abstract FileVO create();

	/**
	 * 清空map信息
	 */
	private void clear() {
		ftlMap.clear();
		pathMap.clear();
	}

	public final FileVO createProject(BaseProjectConfig baseProjectConfig) {
		projectConfig = baseProjectConfig;
		initParentDirMap();
		initPath();
		resolve();
		FileVO fileVO = create();
		clear();
		return fileVO;
	}

}
