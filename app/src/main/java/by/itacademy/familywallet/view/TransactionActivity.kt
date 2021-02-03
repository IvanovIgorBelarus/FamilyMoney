package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.data.FirebaseRepository
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.di.TRANSACTION_TYPE
import by.itacademy.familywallet.utils.PreparationTransactionActivity
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private var transactionType: String? = null
    private val preparationTransactionActivity: PreparationTransactionActivity by inject {
        parametersOf(binding, transactionType)
    }
    private val db: FirebaseRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val uid=FirebaseAuth.getInstance().uid
        if (intent != null) {
            transactionType = intent.getStringExtra(TRANSACTION_TYPE)
        }
        if (transactionType != null) {
            preparationTransactionActivity.setItemsStyles()
        }
        with(binding) {
            cashButton.setOnClickListener {
                createDialog()
                doTransaction()
            }
            cardButton.setOnClickListener { createDialog() }
        }
    }

    private fun doTransaction() {
        if (transactionType != null) {
            db.instance.collection(transactionType!!).add(
                mapOf(
                    "date" to SimpleDateFormat("DD/MM/yyyy").parse(binding.date.text.toString()),
                    "value" to binding.transactionValue.text.toString().toDouble(),
                    //  "transactiontype" to binding.

                )
            )
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