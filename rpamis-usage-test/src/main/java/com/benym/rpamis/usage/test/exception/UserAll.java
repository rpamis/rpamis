package com.benym.rpamis.usage.test.exception;

import com.benym.rpamis.common.exception.annotation.SpecifiesValueValidator;
import com.benym.rpamis.usage.test.exception.enums.PhoneBrandEnums;
import com.benym.rpamis.usage.test.exception.interfaces.ValidatedAction;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author benym
 * @date 2023/1/31 15:43
 */
public class UserAll {

    @NotNull(message = "用户名不能为空", groups = {ValidatedAction.Insert.class})
    private String userName;

    @Range(min = 1, max = 150, message = "年龄必须在1-150区间")
    private Integer age;

    @NotEmpty(message = "用户的兴趣不能为空")
    private List<String> interest;

    @Valid
    private List<Friend> friends;

    @NotNull(message = "手机品牌不能为空")
    @SpecifiesValueValidator(message = "手机品牌需符合枚举", enumClass = PhoneBrandEnums.class)
    private String phoneBrand;

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

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }
}
