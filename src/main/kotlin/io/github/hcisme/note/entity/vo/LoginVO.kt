package io.github.hcisme.note.entity.vo

import io.github.hcisme.note.entity.constants.Constants
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class LoginVO {
    @NotEmpty(message = "账号不能为空")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过150个字符")
    lateinit var email: String

    @Pattern(regexp = Constants.REGEX_PASSWORD, message = "密码格式不正确")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 18, message = "密码长度必须在8到18个字符之间")
    lateinit var password: String

    @NotEmpty(message = "验证码Key不能为空")
    lateinit var captchaKey: String

    @NotEmpty(message = "验证码不能为空")
    lateinit var captcha: String
}
