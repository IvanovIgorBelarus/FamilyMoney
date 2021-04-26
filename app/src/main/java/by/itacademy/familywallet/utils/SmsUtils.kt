package by.itacademy.familywallet.utils

import by.itacademy.familywallet.model.UIModel

object SmsUtils {

    fun getValueFromSms(message: String): UIModel.SmsModel {
        if (message.contains("Spr.:5099999")) {
            return getSmsModelFromMTBank(message)
        }
        if (message.contains("Uspeshno")) {
            return getSmsModelFromAlphaBank(message)
        }
        return UIModel.SmsModel()
    }

    private fun getSmsModelFromMTBank(message: String): UIModel.SmsModel {
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

    private fun getSmsModelFromAlphaBank(message: String): UIModel.SmsModel {
        val list = message.replace("\n", " ").split(" ")
        val date = list[16].formatToDate?.time
        val value = list[5].substring(6).toDouble()
        val currency = list[6]
        return UIModel.SmsModel(
            date = date,
            value = value,
            currency = currency
        )
    }
}