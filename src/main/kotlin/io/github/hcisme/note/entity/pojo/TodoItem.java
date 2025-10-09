package io.github.hcisme.note.entity.pojo;

import java.util.Date;

public class TodoItem {
    /**
     * 主键ID
     * 数据库字段：id INT NOT NULL AUTO_INCREMENT
     * 自增主键
     */
    private Integer id;

    /**
     * 创建者ID
     * 数据库字段：user_id VARCHAR(12) NOT NULL
     * 外键关联用户表，索引(idx_user_id, idx_user_completed)，最大长度12字符
     */
    private String userId;

    /**
     * 标题
     * 数据库字段：title VARCHAR(20) DEFAULT NULL
     * 最大长度20字符，可为空
     */
    private String title;

    /**
     * 内容
     * 数据库字段：content TEXT NOT NULL
     * 待办事项的详细内容
     */
    private String content;

    /**
     * 是否完成
     * 数据库字段：is_completed TINYINT(1) NOT NULL DEFAULT '0'
     * 0-未完成, 1-已完成，索引(idx_is_completed, idx_user_completed)
     */
    private Boolean completed;

    /**
     * 创建时间
     * 数据库字段：created_time DATETIME NOT NULL
     * 记录待办事项创建的时间，索引(idx_created_time)
     */
    private Date createdTime;

    /**
     * 最后修改时间
     * 数据库字段：updated_time DATETIME NOT NULL
     * 记录待办事项最后修改的时间
     */
    private Date updatedTime;

    public TodoItem() {
    }

    public TodoItem(Integer id, String userId, String title, String content, Boolean completed, Date createdTime, Date updatedTime) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.completed = completed;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", completed=" + completed +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
