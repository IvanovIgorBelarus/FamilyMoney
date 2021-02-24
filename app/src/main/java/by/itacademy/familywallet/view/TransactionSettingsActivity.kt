package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding
import by.itacademy.familywallet.utils.PreparationTransactionSettingsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                repo.addNewCategory(
                    category = binding.itemName.text.toString(),
                    type = transactionType!!
                )
            }
            finish()
        }
    }

    companion object {
        fun start(context: Context?, transactionType: String) =
            Intent(context, TransactionSettingsActivity::class.java).apply {
                putExtra(TRANSACTION_TYPE, transactionType)
            }
    }
}