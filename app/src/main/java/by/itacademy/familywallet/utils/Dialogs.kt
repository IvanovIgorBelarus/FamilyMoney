package by.itacademy.familywallet.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import by.itacademy.familywallet.features.start.App.Companion.endDate
import by.itacademy.familywallet.features.start.App.Companion.startDate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.core.others.BANK_MINUS
import by.itacademy.familywallet.core.others.BANK_PLUS
import by.itacademy.familywallet.core.api.DataRepository
import by.itacademy.familywallet.core.others.FULL_DATE
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.others.BaseFragment
import by.itacademy.familywallet.features.new_category.presentation.NewCategoryFragment
import by.itacademy.familywallet.features.transaction.presentation.TransactionFragment
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
            .window
            ?.setBackgroundDrawableResource(R.color.tabBackgroundColor)
    }

    fun deleteDialog(item: Any?, fragment: BaseFragment<*, *>) {
        val context = fragment.context!!
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.alert))
            .setMessage(context.getString(R.string.alert_dialog_delete_item_message))
            .setNeutralButton(context.getString(R.string.cancel)) { _, _ -> }
            .setNegativeButton(context.getString(R.string.update)) { dialog, _ ->
                when (item) {
                    is UIModel.CategoryModel -> fragment.addFragment(NewCategoryFragment.newInstance(item))
                    is UIModel.TransactionModel -> fragment.addFragment(TransactionFragment.newInstance(item))
                }
                dialog.cancel()
            }
            .setPositiveButton(context.getString(R.string.delete)) { dialog, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    repo.deleteItem(item)
                    withContext(Dispatchers.Main) {
                        dialog.cancel()
                    }
                }
            }
            .show()
            .window
            ?.setBackgroundDrawableResource(R.color.tabBackgroundColor)
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

    fun createTransactionDialog(fragment: BaseFragment<*, *>, item: UIModel.TransactionModel, isUpdate: Boolean = false, isSms: Boolean = false) {
        val context = fragment.context!!
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.warning))
            .setMessage(message(context, item))
            .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    if (isUpdate) {
                        repo.upDateItem(item)
                    } else if (item.moneyType == BANK_MINUS || item.moneyType == BANK_PLUS) {
                        repo.doBakTransactions(item)
                    } else {
                        repo.doTransaction(item, isSms)
                    }
                }
                dialog.cancel()
                fragment.onBack()
            }
            .setNegativeButton(context.getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
            .window
            ?.setBackgroundDrawableResource(R.color.tabBackgroundColor)
    }

    private fun message(context: Context, transactionModel: UIModel.TransactionModel): String {
        return if (transactionModel.moneyType == BANK_MINUS || transactionModel.moneyType == BANK_PLUS) {
            context.getString(R.string.message1)
        } else {
            String.format(context.getString(R.string.message), transactionModel.category)
        }
    }
}