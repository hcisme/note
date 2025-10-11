package io.github.hcisme.note.utils

import io.github.hcisme.note.entity.enums.DateTimePatternEnum
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间扩展函数
 */

/**
 * 格式化 Date 格式
 */
fun Date.toFormattedString(pattern: String = DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.pattern): String {
    return SimpleDateFormat(pattern).format(this)
}