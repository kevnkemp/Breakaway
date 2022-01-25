package com.chaboi.breakaway.util

import com.cesarferreira.tempo.isToday
import com.cesarferreira.tempo.isTomorrow
import com.cesarferreira.tempo.isYesterday
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val UTC_TIMEZONE = "UTC"
const val DATE_FORMAT_HOUR_MINUTES = "hh:mm aa"
const val DATE_FORMAT_MONTH_DAY = "MM/dd"
const val TODAY = "Today"
const val YESTERDAY = "Yesterday"
const val TOMORROW = "Tomorrow"
const val FINAL = "Final"
const val POSTPONED = "Postponed"
const val IN_PROGRESS = "In Progress"

fun String.getDateHourMinutesFormat(): String {
    val utcFormatter = SimpleDateFormat(DATE_FORMAT_UTC)
    utcFormatter.timeZone = TimeZone.getTimeZone(UTC_TIMEZONE)
    val utcDate = utcFormatter.parse(this)
    val localDate = SimpleDateFormat(DATE_FORMAT_HOUR_MINUTES, Locale.ENGLISH)
    localDate.timeZone = TimeZone.getDefault()
    return localDate.format(utcDate)
}

fun String.getDateMonthDayFormat(): String {
    val utcFormatter = SimpleDateFormat(DATE_FORMAT_UTC)
    utcFormatter.timeZone = TimeZone.getTimeZone(UTC_TIMEZONE)
    val utcDate = utcFormatter.parse(this)
    val localDate = SimpleDateFormat(DATE_FORMAT_MONTH_DAY, Locale.ENGLISH)
    localDate.timeZone = TimeZone.getDefault()
    return when {
        utcDate.isToday -> TODAY
        utcDate.isTomorrow -> TOMORROW
        utcDate.isYesterday -> YESTERDAY
        else -> localDate.format(utcDate)
    }
}