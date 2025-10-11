package io.github.hcisme.note.entity.vo

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.io.Serializable

class UpdateUserInfoVO : Serializable {
    @NotEmpty(message = "账号不能为空")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    lateinit var email: String

    @NotEmpty(message = "昵称不能为空")
    @Size(max = 12, message = "昵称长度不能超过12个字符")
    lateinit var username: String
}
