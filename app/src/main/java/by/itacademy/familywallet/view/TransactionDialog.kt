package by.itacademy.familywallet.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import by.itacademy.familywallet.R

class TransactionDialog(private val transactionType: String?) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(getString(R.string.warning))
                .setMessage("${getString(R.string.message)} $transactionType?")
                .setPositiveButton(getString(R.string.ok)) { dialog, id ->
                    dialog.cancel()
                    activity?.finish()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        }
    }
}