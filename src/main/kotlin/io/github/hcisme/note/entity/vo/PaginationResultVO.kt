package io.github.hcisme.note.entity.vo

data class PaginationResultVO<T>(
    var totalCount: Int? = null,
    var pageSize: Int? = null,
    var page: Int? = null,
    var pageTotal: Int? = null,
    var list: List<T> = listOf()
) {
    constructor(totalCount: Int, pageSize: Int, page: Int, list: List<T>) : this(
        totalCount,
        pageSize,
        page,
        null,
        list
    )

    constructor(totalCount: Int, pageSize: Int, page: Int, pageTotal: Int, list: List<T>) : this() {
        this.totalCount = totalCount
        this.pageSize = pageSize
        this.page = if (page == 0) 1 else page
        this.pageTotal = pageTotal
        this.list = list
    }

    constructor(list: List<T>) : this(null, null, null, null, list)

    constructor() : this(null, null, null, null, listOf())
}
