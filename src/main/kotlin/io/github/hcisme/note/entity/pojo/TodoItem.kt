package io.github.hcisme.note.entity.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

class TodoItem {
    /**
     * 主键ID
     * 数据库字段：id INT NOT NULL AUTO_INCREMENT
     * 自增主键
     */
    var id: Int? = null

    /**
     * 创建者ID
     * 数据库字段：user_id VARCHAR(12) NOT NULL
     * 外键关联用户表，索引(idx_user_id, idx_user_completed)，最大长度12字符
     */
    var userId: String? = null

    /**
     * 标题
     * 数据库字段：title VARCHAR(20) DEFAULT NULL
     * 最大长度20字符，可为空
     */
    var title: String? = null

    /**
     * 内容
     * 数据库字段：content TEXT NOT NULL
     * 待办事项的详细内容
     */
    var content: String? = null

    /**
     * 是否完成
     * 数据库字段：is_completed TINYINT(1) NOT NULL DEFAULT '0'
     * 0-未完成, 1-已完成，索引(idx_is_completed, idx_user_completed)
     */
    var isCompleted: Int? = null

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var startTime: Date? = null

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var endTime: Date? = null

    /**
     * 创建时间
     * 数据库字段：created_time DATETIME NOT NULL
     * 记录待办事项创建的时间，索引(idx_created_time)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdTime: Date? = null

    /**
     * 最后修改时间
     * 数据库字段：updated_time DATETIME NOT NULL
     * 记录待办事项最后修改的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedTime: Date? = null

    override fun toString(): String {
        return "TodoItem{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isCompleted=" + isCompleted +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}'
    }
}
