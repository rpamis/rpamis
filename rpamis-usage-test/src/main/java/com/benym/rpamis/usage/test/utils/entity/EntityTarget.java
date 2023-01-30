package com.benym.rpamis.usage.test.utils.entity;

import java.io.Serializable;

/**
 * @author benym
 * @date 2022/11/23 20:54
 */
public class EntityTarget implements Serializable {

    private static final long serialVersionUID = 8449069812090444133L;

    private String id;

    private Integer age;

    private Long height;

    private Byte Status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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
