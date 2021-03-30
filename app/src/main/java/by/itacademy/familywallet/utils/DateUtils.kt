package by.itacademy.familywallet.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun parseDate(date: Date, pattern: String, locale: Locale = Locale.getDefault()): String {
        val dateFormat = SimpleDateFormat(pattern, locale)
        return dateFormat.format(date)
    }
    fun parseDateDown(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.time
    }

    fun parseDateUp(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }
}

val Date.toStartOfDay: Date
    get() = DateUtils.parseDateDown(this)

val Date.toEndOfDay: Date
    get() = DateUtils.parseDateUp(this)

fun Date.formatDate(pattern: String): String {
    return DateUtils.parseDate(this, pattern)
}