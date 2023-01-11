package com.benym.rpas.architecture.pojo;

/**
 * @author benym
 * @date 2022/7/27 3:36 下午
 */
public class Database {

    private Boolean enabled;

    private String host;

    private String port;

    private String databaseName;

    private String userName;

    private String passWord;

    private Boolean crud = false;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

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

    public Boolean getCrud() {
        return crud;
    }

    public void setCrud(Boolean crud) {
        this.crud = crud;
    }
}
