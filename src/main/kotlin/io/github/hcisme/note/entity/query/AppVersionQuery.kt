package io.github.hcisme.note.entity.query

/**
 * APP版本管理表参数
 */
class AppVersionQuery : BaseParam() {
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

    var versionNameFuzzy: String? = null

    /**
     * 下载地址
     */
    var downloadUrl: String? = null

    var downloadUrlFuzzy: String? = null

    /**
     * 文件大小(字节)
     */
    var fileSize: Long? = null

    /**
     * 文件MD5校验值
     */
    var fileMd5: String? = null

    var fileMd5Fuzzy: String? = null

    /**
     * 更新内容
     */
    var updateContent: String? = null

    var updateContentFuzzy: String? = null

    /**
     * 发布时间
     */
    var publishTime: String? = null

    var publishTimeStart: String? = null

    var publishTimeEnd: String? = null

    /**
     * 创建时间
     */
    var createdTime: String? = null

    var createdTimeStart: String? = null

    var createdTimeEnd: String? = null

    /**
     * 更新时间
     */
    var updatedTime: String? = null

    var updatedTimeStart: String? = null

    var updatedTimeEnd: String? = null
}
