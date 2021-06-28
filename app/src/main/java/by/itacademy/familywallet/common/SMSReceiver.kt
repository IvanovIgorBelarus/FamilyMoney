package by.itacademy.familywallet.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import by.itacademy.familywallet.common.wrappers.SmsWrapper
import by.itacademy.familywallet.core.others.CURRENCY
import by.itacademy.familywallet.core.others.DATE
import by.itacademy.familywallet.core.firebase.FirebaseDataBase
import by.itacademy.familywallet.core.others.NEW_SMS
import by.itacademy.familywallet.core.others.VALUE
import by.itacademy.familywallet.utils.SmsUtils
import org.greenrobot.eventbus.EventBus

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.action) {
            val sms = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            sms.forEach {
                val sms = SmsUtils.getValueFromSms(it.messageBody)
                if (sms.value != null && sms.value != 0.0) {
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