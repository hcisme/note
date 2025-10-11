package io.github.hcisme.note.service

import io.github.hcisme.note.entity.vo.TokenUserInfoVO

/**
 * 用户表 业务接口
 */
interface UserService {
    fun register(email: String, username: String, password: String)

    fun login(email: String, password: String): TokenUserInfoVO

    fun updateUserInfo(userInfo: TokenUserInfoVO, email: String, username: String)
}