package com.benym.rpamis.usage.test.exception.service;

import com.benym.rpamis.common.dto.response.Response;
import com.benym.rpamis.usage.test.exception.User;

/**
 * @author benym
 * @date 2023/2/6 18:45
 */
public interface TestService {
    User getUser(String id);

    Response<User> getUserWrap(String id);
}
