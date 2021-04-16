package by.itacademy.familywallet.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import by.itacademy.familywallet.data.CURRENCY
import by.itacademy.familywallet.data.DATE
import by.itacademy.familywallet.data.FirebaseDataBase
import by.itacademy.familywallet.data.NEW_SMS
import by.itacademy.familywallet.data.VALUE
import by.itacademy.familywallet.utils.SmsUtils
import org.greenrobot.eventbus.EventBus

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.action) {
            val sms = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            sms.forEach {
                if (it.messageBody.contains("OPLATA")) {
                    val sms = SmsUtils.getSmsModelFromMTBank(it.messageBody)
                    context!!.getSharedPreferences(NEW_SMS, Context.MODE_PRIVATE).edit().putString(NEW_SMS, NEW_SMS).apply()
                    EventBus.getDefault().post(SmsWrapper())
                    FirebaseDataBase.instance.collection(NEW_SMS).add(
                        mapOf(
                            DATE to sms.date,
                            CURRENCY to sms.currency,
                            VALUE to sms.value
                        )
                    )
                }
            }
        }
    }
}