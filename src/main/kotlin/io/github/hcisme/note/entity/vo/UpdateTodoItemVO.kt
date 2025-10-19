package io.github.hcisme.note.entity.vo

import jakarta.validation.constraints.*

class UpdateTodoItemVO {
    @NotNull(message = "content不能为空")
    var id: Int? = null

    @NotEmpty(message = "标题不能为空")
    @Size(max = 20, message = "昵称长度不能超过20个字符")
    lateinit var title: String

    @NotEmpty(message = "描述不能为空")
    lateinit var content: String

    @NotNull(message = "completed不能为空")
    @Min(value = 0, message = "completed只能为0或1")
    @Max(value = 1, message = "completed只能为0或1")
    var completed: Int? = null

    @NotEmpty(message = "开始时间不能为空")
    lateinit var startTime: String

    @NotEmpty(message = "结束时间不能为空")
    lateinit var endTime: String
}