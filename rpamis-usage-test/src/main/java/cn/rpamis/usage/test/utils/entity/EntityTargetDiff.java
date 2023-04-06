package cn.rpamis.usage.test.utils.entity;

import java.io.Serializable;

/**
 * @author benym
 * @date 2022/11/23 20:55
 */
public class EntityTargetDiff implements Serializable {

    private static final long serialVersionUID = 2270614987995919358L;

    private Integer id;

    private String age;

    private Long height;

    private Byte Status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Byte getStatus() {
        return Status;
    }

    public void setStatus(Byte status) {
        Status = status;
    }
}
