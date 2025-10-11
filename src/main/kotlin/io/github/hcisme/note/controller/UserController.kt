package io.github.hcisme.note.controller

import io.github.hcisme.note.annotation.Access
import io.github.hcisme.note.entity.constants.Constants
import io.github.hcisme.note.entity.vo.*
import io.github.hcisme.note.redis.RedisUtils
import io.github.hcisme.note.redis.cleanTokenByToken
import io.github.hcisme.note.redis.saveCaptcha
import io.github.hcisme.note.redis.saveTokenUserInfo
import io.github.hcisme.note.service.UserService
import io.springboot.captcha.ArithmeticCaptcha
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.Resource
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 用户表 Controller
 */
@Tag(name = "账号管理")
@RestController("userController")
@RequestMapping("/user")
class UserController : ABaseController() {
    @Resource
    private lateinit var userService: UserService

    @Resource
    private lateinit var redisUtils: RedisUtils

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    fun getCheckCode(): ResponseVO<Any> {
        val map = HashMap<String, Any>()
        val captcha = ArithmeticCaptcha(100, 42)
        val code = captcha.text()

        val captchaKey = redisUtils.saveCaptcha(code)
        map["captcha"] = captcha.toBase64()
        map["captchaKey"] = captchaKey

        return getSuccessResponseVO(map)
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    fun register(@Validated @RequestBody registerDto: RegisterVO): ResponseVO<Any?> {
        return checkCaptcha(captcha = registerDto.captcha, captchaKey = registerDto.captchaKey) {
            userService.register(registerDto.email, registerDto.username, registerDto.password)
            getSuccessResponseVO(null)
        }
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    fun login(@Validated @RequestBody loginDTO: LoginVO): ResponseVO<TokenUserInfoVO> {
        return checkCaptcha(captcha = loginDTO.captcha, captchaKey = loginDTO.captchaKey) {
            val tokenUserInfoVO = userService.login(loginDTO.email, loginDTO.password)
            getSuccessResponseVO(tokenUserInfoVO)
        }
    }

    @Operation(summary = "自动登录 续token")
    @Access
    @GetMapping("/autoLogin")
    fun autoLogin(): ResponseVO<TokenUserInfoVO?> {
        val tokenUserInfo = getUserInfoByToken()!!

        // 小于 三天 有效期 自动续token
        if (tokenUserInfo.expireAt!! - System.currentTimeMillis() < Constants.REDIS_KEY_EXPIRES_ONE_DAY * 3) {
            redisUtils.cleanTokenByToken(tokenUserInfo.token)
            redisUtils.saveTokenUserInfo(tokenUserInfo)
        }

        return getSuccessResponseVO(tokenUserInfo)
    }

    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    fun logout(): ResponseVO<Any?> {
        val tokenUserInfo = getUserInfoByToken() ?: return getSuccessResponseVO(null)

        redisUtils.cleanTokenByToken(tokenUserInfo.token)
        return getSuccessResponseVO(null)
    }

    @Operation(summary = "获取登陆用户的信息")
    @Access
    @GetMapping("/userInfo")
    fun getUserInfo(): ResponseVO<TokenUserInfoVO?> = getSuccessResponseVO(getUserInfoByToken())

    @Operation(summary = "修改用户信息")
    @Access
    @PutMapping("/userInfo")
    fun updateUserInfo(@Validated @RequestBody newUserInfoVO: UpdateUserInfoVO): ResponseVO<Any?> {
        val userInfo = getUserInfoByToken()!!

        userService.updateUserInfo(
            userInfo = userInfo,
            email = newUserInfoVO.email,
            username = newUserInfoVO.username
        )

        return getSuccessResponseVO(null)
    }
}