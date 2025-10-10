package io.github.hcisme.note.entity.query

import io.github.hcisme.note.entity.enums.PageSizeEnum

class SimplePage {
    var page: Int = 0
    var countTotal: Int = 0
        private set
    var pageSize: Int = 0
    var pageTotal: Int = 0
    var start: Int = 0
    var end: Int = 0

    constructor(start: Int, end: Int) {
        this.start = start
        this.end = end
    }

    constructor(page: Int?, countTotal: Int = 0, pageSize: Int = 0) {
        this.page = page ?: 0
        this.countTotal = countTotal
        this.pageSize = pageSize
        action()
    }

    private fun action() {
        if (pageSize <= 0) {
            pageSize = PageSizeEnum.SIZE20.size
        }
        pageTotal = if (countTotal > 0) {
            if (countTotal % pageSize == 0) countTotal / pageSize else countTotal / pageSize + 1
        } else {
            1
        }
        if (page <= 1) {
            page = 1
        }
        if (page > pageTotal) {
            page = pageTotal
        }
        start = (page - 1) * pageSize
        end = pageSize
    }

    fun setCountTotal(countTotal: Int) {
        this.countTotal = countTotal
        action()
    }
}