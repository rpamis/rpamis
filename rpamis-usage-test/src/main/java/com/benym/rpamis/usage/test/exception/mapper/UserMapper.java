package com.benym.rpamis.usage.test.exception.mapper;

import com.benym.rpamis.usage.test.exception.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author benym
 * @date 2023/2/6 19:00
 */
@Mapper
public interface UserMapper {

    User getUserByUserId(@Param("id") String id);

    int insert(User user);

    int update(User user);
}
