package by.itacademy.familywallet.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

class SMSReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.action) {
            val sms = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            sms.forEach {
                if (it.messageBody.contains("Конфиденциально!")){

                }
            }
        }
    }
}