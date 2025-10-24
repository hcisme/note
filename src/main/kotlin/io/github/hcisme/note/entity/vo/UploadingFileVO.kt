package io.github.hcisme.note.entity.vo

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class UploadingFileVO {
    @NotEmpty(message = "用户名不能为空")
    var username: String? = null

    @NotEmpty(message = "密码不能为空")
    var password: String? = null

    @NotEmpty(message = "上传ID 不能为空")
    var uploadId: String? = null

    @NotNull(message = "chunkIndex 不能为空")
    var chunkIndex: Int? = null
}
