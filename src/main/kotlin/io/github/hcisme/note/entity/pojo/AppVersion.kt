package io.github.hcisme.note.entity.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.*

/**
 * APP版本管理表
 */
class AppVersion : Serializable {
    /**
     *
     */
    var id: Long? = null

    /**
     * 版本号(数字)，用于比较版本新旧
     */
    var versionCode: Int? = null

    /**
     * 版本名称(字符串)，如：1.0.0
     */
    var versionName: String? = null

    /**
     * 下载地址
     */
    var downloadUrl: String? = null

    /**
     * 文件大小(字节)
     */
    var fileSize: Long? = null

    /**
     * 文件MD5校验值
     */
    var fileMd5: String? = null

    /**
     * 更新内容
     */
    var updateContent: String? = null

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var publishTime: Date? = null

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdTime: Date? = null

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedTime: Date? = null

    override fun toString(): String {
        return "AppVersion(id=$id, versionCode=$versionCode, versionName=$versionName, downloadUrl=$downloadUrl, fileSize=$fileSize, fileMd5=$fileMd5, updateContent=$updateContent, publishTime=$publishTime, createdTime=$createdTime, updatedTime=$updatedTime)"
    }
}
