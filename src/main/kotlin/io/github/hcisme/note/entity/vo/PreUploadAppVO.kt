package io.github.hcisme.note.entity.vo

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class PreUploadAppVO {
    @NotEmpty(message = "用户名不能为空")
    var username: String? = null

    @NotEmpty(message = "密码不能为空")
    var password: String? = null

    @NotNull(message = "版本代码不能为空")
    var versionCode: Int? = null

    @NotNull(message = "文件尺寸 不能为空")
    var fileSize: Long? = null

    @NotNull(message = "chunks 不能为空")
    var chunks: Int? = null
}