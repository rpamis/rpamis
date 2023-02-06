package com.benym.rpamis.usage.test.exception.service.impl;

import com.benym.rpamis.common.dto.response.Response;
import com.benym.rpamis.usage.test.exception.User;
import com.benym.rpamis.usage.test.exception.dao.TestDao;
import com.benym.rpamis.usage.test.exception.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author benym
 * @date 2023/2/6 18:46
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public User getUser(String id) {
        // 写一些业务逻辑，比如转换id等等
        return testDao.getUserById(id);
    }

    @Override
    public Response<User> getUserWrap(String id) {
        // 写一些业务逻辑，比如转换id等等
        return testDao.getUserByIdWrap(id);
    }
}
