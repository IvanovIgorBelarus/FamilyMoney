package by.itacademy.familywallet.utils

import by.itacademy.familywallet.model.UIModel
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import java.util.*

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
        var date: Long? = try {
            list[2].formatToDate?.time
        } catch (e: Exception) {
            Firebase.crashlytics.log("getSmsModelFromAlphaBank date")
            Calendar.getInstance().time.time
        }
        var value: Double? = try {
            list[5].toDouble()
        } catch (e: Exception) {
            Firebase.crashlytics.log("getSmsModelFromAlphaBank value")
            null
        }
        var currency: String? = try {
            list[6]
        } catch (e: Exception) {
            Firebase.crashlytics.log("getSmsModelFromAlphaBank currency")
            null
        }
        return UIModel.SmsModel(
            date = date,
            value = value,
            currency = currency
        )
    }

    private fun getSmsModelFromAlphaBank(message: String): UIModel.SmsModel {
        val list = message.replace("\n", " ").split(" ")
        var date: Long? = try {
            list[16].formatToDate?.time
        } catch (e: Exception) {
            Firebase.crashlytics.log("getSmsModelFromAlphaBank date")
            Calendar.getInstance().time.time
        }
        var value: Double? = try {
            list[5].substring(6).toDouble()
        } catch (e: Exception) {
            Firebase.crashlytics.log("getSmsModelFromAlphaBank value")
            0.0
        }
        var currency: String? = try {
            list[6]
        } catch (e: Exception) {
            Firebase.crashlytics.log("getSmsModelFromAlphaBank currency")
            null
        }
        return UIModel.SmsModel(
            date = date,
            value = value,
            currency = currency
        )
    }
}