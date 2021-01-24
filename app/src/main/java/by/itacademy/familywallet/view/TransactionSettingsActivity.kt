package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding
import by.itacademy.familywallet.di.ITEM
import by.itacademy.familywallet.di.TRANSACTION_TYPE
import by.itacademy.familywallet.utils.PreparationTransactionSettingsActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TransactionSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionSettingsBinding
    private val preparationTransactionSettingsActivity: PreparationTransactionSettingsActivity by inject {
        parametersOf(binding, transactionType, item)
    }
    private var item: Char? = null
    private var transactionType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(intent) {
            if (this != null) {
                item = getCharExtra(ITEM, '0')
                transactionType = getStringExtra(TRANSACTION_TYPE)
            }
        }
        preparationTransactionSettingsActivity.setItemsStyles()
    }

    companion object {
        fun start(context: Context?, transactionType: String, item: Char) =
            Intent(context, TransactionSettingsActivity::class.java).apply {
                putExtra(TRANSACTION_TYPE, transactionType)
                putExtra(ITEM, item)
            }
    }
}