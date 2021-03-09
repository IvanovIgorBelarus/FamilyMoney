package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.data.CATEGORIES
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.model.CategoryModel
import by.itacademy.familywallet.model.TransactionModel
import by.itacademy.familywallet.utils.PreparationTransactionActivity
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var item: CategoryModel
    private val repo by inject<DataRepository>()
    private val preparationTransactionActivity: PreparationTransactionActivity by inject {
        parametersOf(binding, item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        if (intent != null) {
            item.type = intent.getStringExtra(TRANSACTION_TYPE)
            item.category = intent.getStringExtra(CATEGORIES)
        }
        if (item.type != null) {
            preparationTransactionActivity.setItemsStyles()
        }
        with(binding) {
            date.setOnDateChangeListener { view, year, month, dayOfMonth ->
                view.date = GregorianCalendar(year, month, dayOfMonth).timeInMillis
            }
            cashButton.setOnClickListener {
                createDialog()
                if (item.type != null) {
                    createDialog()
                }
            }
            cardButton.setOnClickListener { createDialog() }
        }
    }

    private fun createDialog() {
        val transactionModel = TransactionModel(
            uid = FirebaseAuth.getInstance().currentUser?.uid,
            transactionType = item.type,
            transactionCategory = item.category,
            value = binding.transactionValue.text.toString().toDouble(),
            date = binding.date.date
        )
        val dialog = TransactionDialog(repo, transactionModel)
        dialog.show(supportFragmentManager, "dialog")
    }

    companion object {
        fun start(context: Context?, item: CategoryModel) =
            Intent(context, TransactionActivity::class.java).apply {
                putExtra(TRANSACTION_TYPE, item.type)
                putExtra(CATEGORIES, item.category)
            }
    }
}