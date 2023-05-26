package cn.rpamis.architecture.template;

import cn.rpamis.architecture.config.BaseProjectConfig;
import cn.rpamis.architecture.pojo.FileVO;
import cn.rpamis.architecture.service.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板方法
 *
 * @author benym
 * @date 2022/7/21 10:19 上午
 */
public abstract class AbstractBuildTemplate {

    @Autowired(required = false)
    protected BuildService buildService;

    protected Map<String, String> pathMap = new HashMap<>(64);

    protected MultiValueMap<String, String> ftlMap = new LinkedMultiValueMap<>(64);

    protected String buildId = "";

    protected BaseProjectConfig rpasConfig;

    /**
     * 获取模版类型
     *
     * @return String
     */
    protected abstract String getTemplateType();

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
     *
     * @return FileVO
     */
    protected abstract FileVO create();

    /**
     * 清空map信息
     */
    private void clear(){
        ftlMap.clear();
        pathMap.clear();
    }

    public final FileVO createProject(BaseProjectConfig baseProjectConfig) {
        rpasConfig = baseProjectConfig;
        initPath();
        resolve();
        FileVO fileVO = create();
        clear();
        return fileVO;
    }
}
