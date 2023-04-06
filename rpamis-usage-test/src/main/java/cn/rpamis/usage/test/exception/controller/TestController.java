package cn.rpamis.usage.test.exception.controller;

import cn.rpamis.common.dto.exception.ExceptionFactory;
import cn.rpamis.common.dto.response.Response;
import cn.rpamis.usage.test.exception.*;
import cn.rpamis.usage.test.exception.enums.PhoneBrandEnums;
import cn.rpamis.usage.test.exception.interfaces.ValidatedAction;
import cn.rpamis.usage.test.exception.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

/**
 * @author benym
 * @date 2023/1/31 16:10
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/validate")
    public String test(@Validated({ValidatedAction.Insert.class, Default.class}) @RequestBody User user) {
        System.out.println(1);
        return "success";
    }

    @PostMapping("/validateDelete")
    public String test2(@Validated({ValidatedAction.Delete.class, Default.class}) @RequestBody User user) {
        System.out.println(1);
        return "success";
    }

    @PostMapping("/validateSp")
    public String test3(@Validated @RequestBody UserSpValid user) {
        System.out.println(1);
        return "success";
    }

    @PostMapping("/validateAll")
    public String test4(@Validated @RequestBody UserAll user) {
        System.out.println(1);
        return "success";
    }

    @PostMapping("/validateAllNoValid")
    public String test5(@RequestBody UserAllNoValid user) {
        if (StringUtils.isEmpty(user.getUserName())) {
            return "用户名不能为空";
        }
        if (user.getAge() < 1 || user.getAge() > 150) {
            return "年龄必须在1-150区间";
        }
        if (CollectionUtils.isEmpty(user.getInterest())) {
            return "用户的兴趣不能为空";
        }
        List<Friend> friends = user.getFriends();
        for (int i = 0; i < friends.size(); i++) {
            Friend friend = friends.get(i);
            if (!CollectionUtils.isEmpty(friends)) {
                String userName = friend.getUserName();
                if (StringUtils.isEmpty(userName)) {
                    return "朋友的用户名不能为空";
                }
                if (!StringUtils.isEmpty(friend.getAge())) {
                    if (friend.getAge() < 1 || friend.getAge() > 150) {
                        return "朋友的年龄必须在1-150区间";
                    }
                }
            }
        }
        String phoneBrand = user.getPhoneBrand();
        boolean inEnums = false;
        for (PhoneBrandEnums phoneBrandEnums : PhoneBrandEnums.values()) {
            if (phoneBrandEnums.getCode().equals(user.getPhoneBrand())) {
                inEnums = true;
                break;
            }
        }
        if (!inEnums) {
            return "手机品牌需符合枚举";
        }
        return "success";
    }

    @PostMapping("/getUser")
    public Response<User> test6(@RequestBody User user) {
        User user1 = testService.getUser(user.getId());
        return Response.success(user1);
    }

    @PostMapping("/getUserWrap")
    public Response<User> test7(@RequestBody User user) {
        Response<User> userWrap = testService.getUserWrap(user.getId());
        return userWrap;
    }

    @PostMapping("/save")
    public Response<Boolean> test8(@RequestBody User user) {
        Response<Boolean> result = testService.saveOrUpdate(user);
        return result;
    }

    @PostMapping("/saveWithException")
    public Response<Boolean> test9(@RequestBody User user) {
        Boolean result = testService.saveOrUpdateWithException(user);
        return Response.success(result);
    }

    @GetMapping("/testException")
    public Boolean test10(){
        try {
            testService.testException();
            return true;
        } catch (Exception e) {
            throw ExceptionFactory.bizException("外层异常测试",e);
        }
    }
}
