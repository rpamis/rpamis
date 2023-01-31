package com.benym.rpamis.usage.test.exception;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author benym
 * @date 2023/1/31 16:57
 */
public class Friend {

    @NotNull(message = "朋友名称不能为空")
    private String userName;

    @Range(min = 1, max = 150, message = "年龄必须在1-150区间")
    private Integer age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
