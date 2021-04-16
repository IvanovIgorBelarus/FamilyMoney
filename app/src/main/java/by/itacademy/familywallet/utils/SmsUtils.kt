package by.itacademy.familywallet.utils

import by.itacademy.familywallet.model.UIModel

object SmsUtils {
    fun getSmsModelFromMTBank(message: String): UIModel.SmsModel {
        val list = message.replace("\n", " ").split(" ")
        val date = list[2].formatToDate?.time
        val value = list[5].toDouble()
        val currency = list[6]
        return UIModel.SmsModel(
            date = date,
            value = value,
            currency = currency
        )
    }
}