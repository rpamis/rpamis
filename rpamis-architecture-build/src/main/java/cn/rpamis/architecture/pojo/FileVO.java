package cn.rpamis.architecture.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * 项目文件VO
 *
 * @author benym
 * @date 2022/7/20 4:59 下午
 */
@Data
@Builder
public class FileVO {

    /**
     * id
     */
    private String id;

    /**
     * 文件路径
     */
    private String filePath;

    public FileVO(String id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }
}
