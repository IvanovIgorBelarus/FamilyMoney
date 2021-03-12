package by.itacademy.familywallet.utils

import android.app.AlertDialog
import android.content.Context
import by.itacademy.familywallet.R

class Dialogs {
    companion object {
        fun createNegativeDialog(context: Context) {
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.alert))
                .setMessage(context.getString(R.string.alert_negative_message_category_create))
                .setPositiveButton(context.getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
                .show()
        }
    }
}