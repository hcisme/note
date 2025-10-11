package io.github.hcisme.note.utils

import io.github.hcisme.note.entity.enums.DateTimePatternEnum
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 时间扩展函数
 */

/**
 * 格式化 Date 格式
 */
fun Date.toFormattedString(pattern: DateTimePatternEnum = DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS): String {
    return SimpleDateFormat(pattern.pattern).format(this)
}

/**
 * 字符串转 Date 对象
 */
fun String.toDate(pattern: DateTimePatternEnum = DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS): Date {
    val formatter = DateTimeFormatter.ofPattern(pattern.pattern)
    val localDateTime = LocalDateTime.parse(this, formatter)
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
}
