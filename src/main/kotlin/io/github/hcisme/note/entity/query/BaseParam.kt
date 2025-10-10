package io.github.hcisme.note.entity.query

open class BaseParam {
    var simplePage: SimplePage? = null
    var page: Int? = null
    var pageSize: Int? = null
    var orderBy: String? = null
}
