package com.benym.rpas.usage.test.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benym.rpas.common.dto.response.PageResponse;
import com.benym.rpas.common.utils.RpasBeanUtils;
import com.benym.rpas.usage.test.utils.converter.DiffConverter;
import com.benym.rpas.usage.test.utils.entity.EntitySource;
import com.benym.rpas.usage.test.utils.entity.EntityTarget;
import com.benym.rpas.usage.test.utils.entity.EntityTargetDiff;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2022/11/23 20:56
 */
public class RpasBeanUtilsUsageTest {

    public static void main(String[] args) {
        EntitySource entitySource = init();
        // new出target拷贝，无返回值
        EntityTarget entityTarget = new EntityTarget();
        RpasBeanUtils.copy(entitySource, entityTarget);

        // 通过target class拷贝，返回target
        entitySource = init();
        EntityTarget copy = RpasBeanUtils.copy(entitySource, EntityTarget.class);

        // 通过converter拷贝，无返回值
        entitySource = init();
        EntityTargetDiff entityTargetDiff = new EntityTargetDiff();
        RpasBeanUtils.copyWithConverter(entitySource, entityTargetDiff, new DiffConverter());

        // 通过converter、target class拷贝，返回target
        entitySource = init();
        RpasBeanUtils.copyWithConverter(entitySource, EntityTargetDiff.class, new DiffConverter());

        // 通过target class拷贝，返回List<target>
        List<EntitySource> list = initList();
        List<EntityTarget> entityTargets = RpasBeanUtils.copyToList(list, EntityTarget.class);

        // 通过converter、target class拷贝，返回List<Target>
        list = initList();
        List<EntityTargetDiff> entityTargetDiffs = RpasBeanUtils.copyToList(list, EntityTargetDiff.class, new DiffConverter());

        // 转换Mybatis-plus Page到PageResponse，返回PageResponse
        Page<EntitySource> page = initPage();
        PageResponse<EntitySource> entitySourcePageResponse = RpasBeanUtils.toPageResponse(page);

        // 通过target class拷贝，返回PageResponse<target>
        PageResponse<EntityTarget> entityTargetPageResponse = RpasBeanUtils.toPageResponse(entitySourcePageResponse, EntityTarget.class);

        // 将page对象转化为PageResponse, function converter形式
        page = initPage();
        PageResponse<EntityTarget> response = RpasBeanUtils.toPageResponse(page, source -> RpasBeanUtils.copy(source, EntityTarget.class));

        // 通过target class拷贝，返回PageResponse<target>，function converter形式
        PageResponse<EntityTargetDiff> pageResponse = RpasBeanUtils.toPageResponse(entitySourcePageResponse,
                source -> RpasBeanUtils.copyWithConverter(source, EntityTargetDiff.class, new DiffConverter()));

    }

    public static EntitySource init() {
        EntitySource entitySource = new EntitySource();
        entitySource.setId("1");
        entitySource.setAge(2);
        entitySource.setStatus((byte) 3);
        entitySource.setHeight(4L);
        return entitySource;
    }

    public static List<EntitySource> initList() {
        List<EntitySource> list = new ArrayList<>();
        EntitySource entitySourceOne = new EntitySource();
        entitySourceOne.setId("1");
        entitySourceOne.setAge(2);
        entitySourceOne.setStatus((byte) 3);
        entitySourceOne.setHeight(4L);

        EntitySource entitySourceTwo = new EntitySource();
        entitySourceTwo.setId("3");
        entitySourceTwo.setAge(4);
        entitySourceTwo.setStatus((byte) 5);
        entitySourceTwo.setHeight(6L);

        list.add(entitySourceOne);
        list.add(entitySourceTwo);
        return list;
    }

    public static Page<EntitySource> initPage() {
        Page<EntitySource> page = new Page<>();
        page.setRecords(initList());
        page.setSize(1);
        page.setCurrent(1);
        page.setTotal(2);
        return page;
    }
}
