package io.github.hcisme.note.entity.vo

data class ResponseVO<T>(
    var status: String? = null,
    var code: Int? = null,
    var info: String? = null,
    var data: T? = null
)
