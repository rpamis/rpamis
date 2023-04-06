package cn.rpamis.usage.test.exception;

import cn.rpamis.usage.test.exception.interfaces.ValidatedAction;
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
public class User {

    @NotNull(message = "id不能为空", groups = {ValidatedAction.Delete.class})
    private String id;

    @NotNull(message = "用户名不能为空", groups = {ValidatedAction.Insert.class})
    private String userName;

    @NotNull(message = "密码不能为空", groups = {ValidatedAction.Insert.class, ValidatedAction.Delete.class})
    @Pattern(regexp = "^[a-zA-Z0-9|_]+$", message = "密码必须由字母、数字、下划线组成")
    @Size(min = 6, max = 12, message = "密码长度必须在6-12字符之间")
    private String passWord;

    @Range(min = 1, max = 150, message = "年龄必须在1-150区间")
    private Integer age;

    @NotEmpty(message = "用户的兴趣不能为空")
    private List<String> interest;

    @Valid
    private List<Friend> friends;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }
}
