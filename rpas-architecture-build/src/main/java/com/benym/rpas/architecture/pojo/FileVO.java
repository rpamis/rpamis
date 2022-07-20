package com.benym.rpas.architecture.pojo;

/**
 * @date 2022/7/20 4:59 下午
 */
public class FileVO {

    private String id;

    private String filePath;

    public FileVO(String id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
