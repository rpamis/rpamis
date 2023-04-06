package cn.rpamis.usage.test.exception.service.impl;

import cn.rpamis.common.dto.enums.ResponseCode;
import cn.rpamis.common.dto.exception.ExceptionFactory;
import cn.rpamis.common.dto.response.Response;
import cn.rpamis.usage.test.exception.User;
import cn.rpamis.usage.test.exception.dao.TestDao;
import cn.rpamis.usage.test.exception.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public Response<Boolean> saveOrUpdate(User user) {
        Response<Boolean> response = new Response<>();
        // 传输id不为空为update，否则为新增
        if (StringUtils.isEmpty(user.getId())) {
            try {
                int insertRow = testDao.insert(user);
                if (insertRow < 1) {
                    response.setErrCode(ResponseCode.FAILED.getCode());
                    response.setErrMessage("新增失败");
                    return response;
                } else {
                    response.setErrCode(ResponseCode.SUCCESS.getCode());
                    response.setErrMessage("新增成功");
                    return response;
                }
            } catch (Exception e) {
                response.setErrCode(ResponseCode.FAILED.getCode());
                response.setErrMessage("新增时异常");
                return response;
            }
        } else {
            try {
                int insertRow = testDao.update(user);
                if (insertRow < 1) {
                    response.setErrCode(ResponseCode.FAILED.getCode());
                    response.setErrMessage("更新失败");
                    return response;
                } else {
                    response.setErrCode(ResponseCode.SUCCESS.getCode());
                    response.setErrMessage("更新成功");
                    return response;
                }
            } catch (Exception e) {
                response.setErrCode(ResponseCode.FAILED.getCode());
                response.setErrMessage("更新时异常");
                return response;
            }
        }
    }

    @Override
    public Boolean saveOrUpdateWithException(User user) {
        if (StringUtils.isEmpty(user.getId())) {
            int insertReuslt = testDao.insertWithException(user);
            return insertReuslt > 0;
        } else {
            int updateReuslt = testDao.updateWithException(user);
            return updateReuslt > 0;
        }
    }

    @Override
    public void testException() {
        throw ExceptionFactory.bizNoStackException("这是一段错误测试");
    }
}
