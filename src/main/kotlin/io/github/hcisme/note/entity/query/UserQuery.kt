package io.github.hcisme.note.entity.query

/**
 * 用户表参数
 */
class UserQuery : BaseParam() {
    /**
     * 用户ID
     */
    var id: String? = null

    var idFuzzy: String? = null

    /**
     * 用户名
     */
    var username: String? = null

    var usernameFuzzy: String? = null

    /**
     * 账号
     */
    var account: String? = null

    var accountFuzzy: String? = null

    /**
     * 密码
     */
    var password: String? = null

    var passwordFuzzy: String? = null

    /**
     * 创建时间
     */
    var createdTime: String? = null

    var createdTimeStart: String? = null

    var createdTimeEnd: String? = null

    /**
     * 最后修改时间
     */
    var updatedTime: String? = null

    var updatedTimeStart: String? = null

    var updatedTimeEnd: String? = null

    /**
     * 最后登录时间
     */
    var lastLoginTime: String? = null

    var lastLoginTimeStart: String? = null

    var lastLoginTimeEnd: String? = null
}
