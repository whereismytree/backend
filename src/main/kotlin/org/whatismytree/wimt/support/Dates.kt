package org.whatismytree.wimt.support

import java.time.LocalDate
import java.time.LocalDateTime

@Suppress("LongParameterList")
fun createLocalDateTime(
    year: Int,
    month: Int = 1,
    dayOfMonth: Int = 1,
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0,
    nanoOfSecond: Int = 0,
): LocalDateTime {
    return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond)
}

fun createLocalDate(
    year: Int,
    month: Int = 1,
    dayOfMonth: Int = 1,
): LocalDate {
    return LocalDate.of(year, month, dayOfMonth)
}
