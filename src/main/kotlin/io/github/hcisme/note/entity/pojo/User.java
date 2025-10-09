package io.github.hcisme.note.entity.pojo;

import java.util.Date;

/**
 * 用户表实体类
 */
public class User {
    /**
     * 用户ID
     * 数据库字段：id VARCHAR(12) NOT NULL
     * 主键，最大长度12字符
     */
    private String id;

    /**
     * 用户名
     * 数据库字段：username VARCHAR(12) NOT NULL
     * 普通索引(idx_username)，最大长度12字符
     */
    private String username;

    /**
     * 账号
     * 数据库字段：account VARCHAR(50) NOT NULL
     * 唯一索引(idx_account)，最大长度50字符
     */
    private String account;

    /**
     * 密码
     * 数据库字段：password VARCHAR(255) NOT NULL
     * 加密存储，最大长度255字符
     */
    private String password;

    /**
     * 创建时间
     * 数据库字段：created_time DATETIME NOT NULL
     * 记录用户账号创建的时间
     */
    private Date createdTime;

    /**
     * 最后修改时间
     * 数据库字段：updated_time DATETIME NOT NULL
     * 记录用户信息最后修改的时间
     */
    private Date updatedTime;

    /**
     * 最后登录时间
     * 数据库字段：last_login_time DATETIME NOT NULL
     * 记录用户最后一次登录的时间
     */
    private Date lastLoginTime;

    public User() {
    }

    public User(
            String id,
            String username,
            String account,
            String password,
            Date createdTime,
            Date updatedTime,
            Date lastLoginTime
    ) {
        this.id = id;
        this.username = username;
        this.account = account;
        this.password = password;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.lastLoginTime = lastLoginTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
