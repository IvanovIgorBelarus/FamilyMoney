package by.itacademy.familywallet.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.model.TransactionModel
import by.itacademy.familywallet.utils.Transaction

class TransactionDialog(
    private val transactionType: String?,
    private val transaction: Transaction,
    private val transactionModel: TransactionModel
    ) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(getString(R.string.warning))
                .setMessage("${getString(R.string.message)} $transactionType?")
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    if (transactionType != null) {
                        transaction.doTransaction(transactionType,transactionModel)
                        dialog.cancel()
                        activity?.finish()
                    }
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        }
    }
}