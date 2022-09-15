package com.benym.rpas.architecture.pojo;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @date 2022/7/20 4:47 下午
 */
public class Project {

    @NotBlank(message = "groupId不能为空")
    @ApiModelProperty("groupId")
    private String groupId;

    @NotBlank(message = "artifactId不能为空")
    @ApiModelProperty("artifactId")
    private String artifactId;

    @ApiModelProperty("maven")
    private String type;

    @ApiModelProperty("jar or war")
    private String packaging;

    @ApiModelProperty("Java版本")
    private String javaVersion;

    @ApiModelProperty("项目版本")
    private String version;

    @NotBlank(message = "packageName不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-]*$", message = "artifactId仅能以英文字母开头，可包含字母、数字、-线")
    @ApiModelProperty("package")
    private String packageName;

    @ApiModelProperty("项目描述")
    private String description;

    @ApiModelProperty("主类名(由artifactId自动转化生成)")
    private String mainName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }
}
