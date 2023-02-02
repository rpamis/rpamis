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
public class UserAllNoValid {

    private String userName;

    private Integer age;

    private List<String> interest;

    private List<Friend> friends;

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

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }
}
