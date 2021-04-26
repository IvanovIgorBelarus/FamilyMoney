package by.itacademy.familywallet.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun parseDate(date: Date, pattern: String, locale: Locale = Locale.getDefault()): String {
        val dateFormat = SimpleDateFormat(pattern, locale)
        return dateFormat.format(date)
    }

    fun parseDateFirstDayOfMonth(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.time
    }

    fun parseDateLastDateOfMonth(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.time
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

val Date.getYearMonth: String
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        val month = when (this.getMonth) {
            0 -> "январь"
            1 -> "февраль"
            2 -> "март"
            3 -> "апрель"
            4 -> "май"
            5 -> "июнь"
            6 -> "июль"
            7 -> "август"
            8 -> "сентябрь"
            9 -> "октябрь"
            10 -> "ноябрь"
            11 -> "декабрь"
            else -> "нэту такова брат"
        }
        return String.format("%s %s", month, calendar.get(Calendar.YEAR))
    }

val Date.getMonth: Int
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar.get(Calendar.MONTH)
    }

val Date.toStartOfDay: Date
    get() = DateUtils.parseDateDown(this)

val Date.toEndOfDay: Date
    get() = DateUtils.parseDateUp(this)

val Date.getFirstDayOfMonth: Date
    get() = DateUtils.parseDateFirstDayOfMonth(this).toStartOfDay

val Date.getLastDayOfMonth: Date
    get() = DateUtils.parseDateLastDateOfMonth(this).toEndOfDay

fun Date.formatDate(pattern: String): String {
    return DateUtils.parseDate(this, pattern)
}

val String.formatToDate: Date?
    get() {
        if (this.contains("-")) {
            return SimpleDateFormat("yyyy-MM-dd").parse(this).toStartOfDay
        }
        return SimpleDateFormat("dd.MM.yyyy").parse(this).toStartOfDay
    }

val Long.toStringFormat: String
    get() = SimpleDateFormat("dd.MM.yyyy").format(this)
