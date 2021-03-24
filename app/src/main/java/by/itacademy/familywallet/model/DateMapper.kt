package by.itacademy.familywallet.model

import java.text.SimpleDateFormat
import java.util.*

class DateMapper : (Long) -> String {
    override fun invoke(date: Long) = SimpleDateFormat("dd/MM/yyyy").format(Date(date))
}