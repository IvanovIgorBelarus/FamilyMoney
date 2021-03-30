package by.itacademy.familywallet.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import by.itacademy.familywallet.App.Companion.endDate
import by.itacademy.familywallet.App.Companion.startDate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK_MINUS
import by.itacademy.familywallet.data.BANK_PLUS
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.FULL_DATE
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.view.TransactionFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Dialogs(private val repo: DataRepository) {
    fun createNegativeDialog(context: Context, message: String) {
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
        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val pickerCalendar = Calendar.getInstance()
            pickerCalendar.set(year, month, dayOfMonth)
            textDate.text = pickerCalendar.time.formatDate(FULL_DATE)
            if (textDate.id == R.id.startDateTextView) {
                startDate = pickerCalendar.time.toStartOfDay.time
            } else {
                endDate = pickerCalendar.time.toEndOfDay.time
            }
        }, year, month, day)
        dialog.datePicker.firstDayOfWeek = 2
        dialog.show()
    }

    fun createTransactionDialog(fragment: TransactionFragment, transactionModel: UIModel.TransactionModel) {
        val context = fragment.context!!
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.warning))
            .setMessage(message(context, transactionModel))
            .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    if (transactionModel.moneyType == BANK_MINUS || transactionModel.moneyType == BANK_PLUS) {
                        repo.doBakTransactions(transactionModel)
                    } else {
                        repo.doTransaction(transactionModel)
                    }
                }
                dialog.cancel()
                fragment.closeFragment()
            }
            .setNegativeButton(context.getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun message(context: Context, transactionModel: UIModel.TransactionModel): String {
        return if (transactionModel.moneyType == BANK_MINUS || transactionModel.moneyType == BANK_PLUS) {
            "${context.getString(R.string.message)} данные?"
        } else {
            "${context.getString(R.string.message)} ${transactionModel.category}?"
        }
    }
}