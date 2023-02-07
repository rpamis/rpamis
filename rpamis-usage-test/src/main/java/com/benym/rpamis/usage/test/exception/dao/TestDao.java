package com.benym.rpamis.usage.test.exception.dao;

import com.benym.rpamis.common.dto.response.Response;
import com.benym.rpamis.usage.test.exception.User;

/**
 * @author benym
 * @date 2023/2/6 18:57
 */
public interface TestDao {
    User getUserById(String id);

    Response<User> getUserByIdWrap(String id);

    int insert(User user);

    int update(User user);
}
