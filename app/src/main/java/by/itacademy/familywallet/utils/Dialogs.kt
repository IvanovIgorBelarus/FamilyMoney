package by.itacademy.familywallet.utils

import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Dialogs(private val repo: DataRepository) {
    fun createNegativeDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.alert))
            .setMessage(context.getString(R.string.alert_negative_message_category_create))
            .setPositiveButton(context.getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    fun deleteDialog(item: Any?, fragment: Fragment) {
        val context = fragment.context!!
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.alert))
            .setMessage(context.getString(R.string.alert_dialog_delete_item_message))
            .setNegativeButton(context.getString(R.string.no)) { _, _ -> }
            .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    repo.deleteItem(item)
                    withContext(Dispatchers.Main) {
                        dialog.cancel()
                        fragment.onResume()
                    }
                }
            }
            .show()
    }
}