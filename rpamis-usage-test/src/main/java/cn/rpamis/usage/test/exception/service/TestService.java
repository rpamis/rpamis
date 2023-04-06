package cn.rpamis.usage.test.exception.service;

import cn.rpamis.common.dto.response.Response;
import cn.rpamis.usage.test.exception.User;

/**
 * @author benym
 * @date 2023/2/6 18:45
 */
public interface TestService {
    User getUser(String id);

    Response<User> getUserWrap(String id);

    Response<Boolean> saveOrUpdate(User user);

    Boolean saveOrUpdateWithException(User user);

    void testException();
}
