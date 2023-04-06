package cn.rpamis.usage.test.exception;

import cn.rpamis.common.exception.annotation.SpecifiesValueValidator;
import cn.rpamis.usage.test.exception.enums.PhoneBrandEnums;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author benym
 * @date 2023/2/2 17:17
 */
public class UserSpValid {


    @NotNull(message = "手机品牌不能为空")
    @SpecifiesValueValidator(message = "手机品牌需符合枚举", enumClass = PhoneBrandEnums.class)
    private String phoneBrand;

    @SpecifiesValueValidator(message = "用户状态需要符合规则", intGroup = {1, 2, 3})
    private Integer status;

    @SpecifiesValueValidator(message = "用户的学校需要符合规则", strGroup = {"11", "22", "33"})
    private String shchool;

    @SpecifiesValueValidator(message = "传输list需要符合规则", strGroup = {"456","789"})
    private List<String> testList;

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShchool() {
        return shchool;
    }

    public void setShchool(String shchool) {
        this.shchool = shchool;
    }

    public List<String> getTestList() {
        return testList;
    }

    public void setTestList(List<String> testList) {
        this.testList = testList;
    }
}
