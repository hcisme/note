package io.github.hcisme.note.entity.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

/**
 * 用户表实体类
 */
class User {
    /**
     * 用户ID
     * 数据库字段：id VARCHAR(12) NOT NULL
     * 主键，最大长度12字符
     */
    var id: String? = null

    /**
     * 用户名
     * 数据库字段：username VARCHAR(12) NOT NULL
     * 普通索引(idx_username)，最大长度12字符
     */
    var username: String? = null

    /**
     * 账号
     * 数据库字段：email VARCHAR(50) NOT NULL
     * 唯一索引(idx_email)，最大长度50字符
     */
    var email: String? = null

    /**
     * 密码
     * 数据库字段：password VARCHAR(255) NOT NULL
     * 加密存储，最大长度255字符
     */
    var password: String? = null

    /**
     * 创建时间
     * 数据库字段：created_time DATETIME NOT NULL
     * 记录用户账号创建的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdTime: Date? = null

    /**
     * 最后修改时间
     * 数据库字段：updated_time DATETIME NOT NULL
     * 记录用户信息最后修改的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedTime: Date? = null

    /**
     * 最后登录时间
     * 数据库字段：last_login_time DATETIME NOT NULL
     * 记录用户最后一次登录的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var lastLoginTime: Date? = null

    override fun toString(): String {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", lastLoginTime=" + lastLoginTime +
                '}'
    }
}
