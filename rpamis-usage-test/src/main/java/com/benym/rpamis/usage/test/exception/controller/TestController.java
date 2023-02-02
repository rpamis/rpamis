package com.benym.rpamis.usage.test.exception.controller;

import com.benym.rpamis.usage.test.exception.User;
import com.benym.rpamis.usage.test.exception.UserSpValid;
import com.benym.rpamis.usage.test.exception.interfaces.ValidatedAction;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.groups.Default;

/**
 * @author benym
 * @date 2023/1/31 16:10
 */
@RestController
@RequestMapping("/test")
public class TestController {

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
}
