package io.github.hcisme.note.service.impl

import io.github.hcisme.note.entity.enums.ResponseCodeEnum
import io.github.hcisme.note.entity.pojo.User
import io.github.hcisme.note.entity.query.UserQuery
import io.github.hcisme.note.entity.vo.TokenUserInfoVO
import io.github.hcisme.note.exception.BusinessException
import io.github.hcisme.note.mappers.UserMapper
import io.github.hcisme.note.redis.RedisUtils
import io.github.hcisme.note.redis.saveTokenUserInfo
import io.github.hcisme.note.redis.updateTokenUserInfo
import io.github.hcisme.note.service.UserService
import io.github.hcisme.note.utils.StringTools
import io.github.hcisme.note.utils.toFormattedString
import jakarta.annotation.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 用户表 业务接口实现
 */
@Service("userService")
class UserServiceImpl : UserService {
    @Resource
    private lateinit var userMapper: UserMapper<User, UserQuery>

    @Resource
    private lateinit var redisUtils: RedisUtils

    override fun register(email: String, username: String, password: String) {
        var user = userMapper.selectByEmail(email)
        if (user != null) {
            throw BusinessException("邮箱已被注册")
        }

        val date = Date()
        val userId = StringTools.generateUserId()

        user = User().apply {
            id = userId
            this.username = username
            this.email = email
            this.password = StringTools.encodeMd5(password)
            this.createdTime = date
            this.updatedTime = date
        }

        userMapper.insert(user)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun login(email: String, password: String): TokenUserInfoVO {
        val user = userMapper.selectByEmail(email)

        if (user == null || user.password != StringTools.encodeMd5(password)) {
            throw BusinessException("账号或密码错误")
        }

        user.lastLoginTime = Date()
        userMapper.updateById(user, user.id!!)

        // 用户信息 存 redis 里
        val tokenUserInfoVO = TokenUserInfoVO().apply {
            this.id = user.id
            this.email = user.email
            this.username = user.username
            this.createdTime = user.createdTime!!.toFormattedString()
            this.updatedTime = user.updatedTime!!.toFormattedString()
            this.lastLoginTime = user.lastLoginTime!!.toFormattedString()
        }
        redisUtils.saveTokenUserInfo(tokenUserInfoVO)
        return tokenUserInfoVO
    }

    override fun updateUserInfo(userInfo: TokenUserInfoVO, email: String, username: String) {
        val userId = userInfo.id!!
        val dbInfo = userMapper.selectById(userId) ?: throw BusinessException(ResponseCodeEnum.CODE_500)
        if (dbInfo.email == email) {
            throw BusinessException("邮箱已存在")
        }
        val date = Date()

        // 更新表
        userMapper.updateById(
            dbInfo.apply {
                this.email = email
                this.username = username
                this.updatedTime = date
            },
            userId
        )

        // 更新 redis
        redisUtils.updateTokenUserInfo(
            userInfo.apply {
                this.email = email
                this.username = username
                this.updatedTime = date.toFormattedString()
            }
        )
    }
}