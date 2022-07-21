package com.benym.rpas.architecture.template;

import com.benym.rpas.architecture.config.BaseProjectConfig;
import com.benym.rpas.architecture.pojo.FileVO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;

/**
 * 模板方法
 *
 * @date 2022/7/21 10:19 上午
 */
public abstract class BuildAbstractTemplate implements CommandLineRunner {

    private Map<String, String> pathMap = new HashMap<>();

    protected abstract void initPath(BaseProjectConfig baseProjectConfig);

    protected abstract void resolve(BaseProjectConfig baseProjectConfig);

    protected abstract FileVO create(BaseProjectConfig baseProjectConfig);

    public final FileVO createProject(BaseProjectConfig baseProjectConfig) {
        initPath(baseProjectConfig);
        resolve(baseProjectConfig);
        FileVO fileVO = create(baseProjectConfig);
        return fileVO;
    }
}
