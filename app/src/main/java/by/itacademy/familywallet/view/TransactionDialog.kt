package by.itacademy.familywallet.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK_MINUS
import by.itacademy.familywallet.data.BANK_PLUS
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.model.UIModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionDialog(
    private val repo: DataRepository,
    private val transactionModel: UIModel.TransactionModel
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(getString(R.string.warning))
                .setMessage(message())
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        if (transactionModel.moneyType == BANK_MINUS || transactionModel.moneyType == BANK_PLUS) {
                            repo.doBakTransactions(transactionModel)
                        } else {
                            repo.doTransaction(transactionModel)
                        }
                    }
                    dialog.cancel()
                    activity?.finish()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        }
    }
    private fun message(): String{
        return if (transactionModel.moneyType == BANK_MINUS || transactionModel.moneyType == BANK_PLUS) {
            "${getString(R.string.message)} данные?"
        } else {
            "${getString(R.string.message)} ${transactionModel.category}?"
        }
    }
}