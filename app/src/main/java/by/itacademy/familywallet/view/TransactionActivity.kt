package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.di.TRANSACTION_TYPE
import by.itacademy.familywallet.utils.PreparationTransactionActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private var transactionType: String? = null
    private val preparationTransactionActivity: PreparationTransactionActivity by inject {
        parametersOf(binding, transactionType)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        if (intent != null) {
            transactionType = intent.getStringExtra(TRANSACTION_TYPE)
        }
        if (transactionType != null) {
            with(preparationTransactionActivity) {
                setItemsStyles()
                createSpinner()
            }
        }
        with(binding) {
            cashButton.setOnClickListener { createDialog() }
            cardButton.setOnClickListener { createDialog() }
        }
    }

    private fun createDialog() {
        val dialog = TransactionDialog(transactionType)
        dialog.show(supportFragmentManager, "dialog")
    }

    companion object {
        fun start(context: Context?, transactionType: String) =
            Intent(context, TransactionActivity::class.java).apply {
                putExtra(TRANSACTION_TYPE, transactionType)
            }
    }
}