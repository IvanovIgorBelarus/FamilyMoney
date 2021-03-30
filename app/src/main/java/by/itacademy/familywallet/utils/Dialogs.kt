package by.itacademy.familywallet.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.App
import by.itacademy.familywallet.App.Companion.endDate
import by.itacademy.familywallet.App.Companion.startDate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.FULL_DATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Dialogs(private val repo: DataRepository) {
    fun createNegativeDialog(context: Context, message:String) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.alert))
            .setMessage(message)
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

    fun createDateDialog(context: Context, textDate: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val pickerCalendar = Calendar.getInstance()
            pickerCalendar.set(year, month, dayOfMonth)
            textDate.text = pickerCalendar.time.formatDate(FULL_DATE)
            if (textDate.id == R.id.startDateTextView) {
                startDate = pickerCalendar.timeInMillis
            } else {
                endDate = pickerCalendar.timeInMillis
            }
        }, year, month, day).show()
    }
}