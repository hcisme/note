package io.github.hcisme.note.entity.vo

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class UploadAppInfoVO {
    @NotEmpty(message = "用户名不能为空")
    var username: String? = null

    @NotEmpty(message = "密码不能为空")
    var password: String? = null

    @NotNull(message = "版本代码不能为空")
    var versionCode: Int? = null

    @NotEmpty(message = "版本号不能为空")
    @Size(max = 50, message = "版本号长度不能超过50个字符")
    var versionName: String? = null

    @NotEmpty(message = "文件 md5 hash不能为空")
    var fileMd5: String? = null

    @NotEmpty(message = "内容描述不能为空")
    var updateContent: String? = null

    @NotEmpty(message = "上传ID 不能为空")
    var uploadId: String? = null
}