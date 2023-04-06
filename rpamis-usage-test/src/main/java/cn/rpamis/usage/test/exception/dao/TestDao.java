package cn.rpamis.usage.test.exception.dao;

import cn.rpamis.common.dto.response.Response;
import cn.rpamis.usage.test.exception.User;

/**
 * @author benym
 * @date 2023/2/6 18:57
 */
public interface TestDao {
    User getUserById(String id);

    Response<User> getUserByIdWrap(String id);

    int insert(User user);

    int insertWithException(User user);

    int update(User user);


    int updateWithException(User user);
}
