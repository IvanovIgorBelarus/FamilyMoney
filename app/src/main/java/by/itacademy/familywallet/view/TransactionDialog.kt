package by.itacademy.familywallet.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import by.itacademy.familywallet.R
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
                .setMessage("${getString(R.string.message)} ${transactionModel.transactionCategory}?")
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        repo.doTransaction(transactionModel)
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
}