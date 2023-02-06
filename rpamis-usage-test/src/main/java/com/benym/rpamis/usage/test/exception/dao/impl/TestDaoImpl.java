package com.benym.rpamis.usage.test.exception.dao.impl;

import com.benym.rpamis.common.dto.enums.ResponseCode;
import com.benym.rpamis.common.dto.response.Response;
import com.benym.rpamis.usage.test.exception.User;
import com.benym.rpamis.usage.test.exception.dao.TestDao;
import com.benym.rpamis.usage.test.exception.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author benym
 * @date 2023/2/6 18:58
 */
@Repository
public class TestDaoImpl implements TestDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String id) {
        User userByUserId = null;
        try {
            // 其他逻辑
            userByUserId = userMapper.getUserByUserId(id);
        } catch (Exception e) {
            throw new RuntimeException("报错了");
        }
        return userByUserId;
    }

    @Override
    public Response<User> getUserByIdWrap(String id) {
        User userByUserId = null;
        try {
            // 其他逻辑
            userByUserId = userMapper.getUserByUserId(id);
        } catch (Exception e) {
            Response<User> response = new Response<>();
            response.setErrCode(ResponseCode.FAILED.getCode());
            response.setErrMessage("报错了");
            return response;
        }
        return Response.success(userByUserId);
    }
}
