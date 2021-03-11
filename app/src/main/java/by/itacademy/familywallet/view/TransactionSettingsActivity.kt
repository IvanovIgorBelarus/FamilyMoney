package by.itacademy.familywallet.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding
import by.itacademy.familywallet.utils.PreparationTransactionSettingsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TransactionSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionSettingsBinding
    private val repo by inject<DataRepository>()
    private val preparationTransactionSettingsActivity: PreparationTransactionSettingsActivity by inject {
        parametersOf(binding, transactionType)
    }
    private var transactionType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(intent) {
            if (this != null) {
                transactionType = getStringExtra(TRANSACTION_TYPE)
            }
        }
        preparationTransactionSettingsActivity.setItemsStyles()
        initSaveButton()
    }

    private fun initSaveButton() {
        binding.saveButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val category = binding.itemName.text.toString()
                if (!category.isNullOrEmpty()) {
                    repo.addNewCategory(
                        category = binding.itemName.text.toString(),
                        type = transactionType!!
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) { createNegativeDialog() }
                }
            }
        }
    }

    private fun createNegativeDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert))
            .setMessage(getString(R.string.alert_negative_message_category_create))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    companion object {
        fun start(context: Context?, transactionType: String) =
            Intent(context, TransactionSettingsActivity::class.java).apply {
                putExtra(TRANSACTION_TYPE, transactionType)
            }
    }
}