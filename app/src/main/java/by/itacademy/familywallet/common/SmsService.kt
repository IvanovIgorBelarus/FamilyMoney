package by.itacademy.familywallet.common

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import by.itacademy.familywallet.data.TAG

class SmsService : Service() {

    override fun onCreate() {
        super.onCreate()
        registerReceiver(SMSReceiver(), IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
        Log.d(TAG,"SmsService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"SmsService onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? = Binder()
}