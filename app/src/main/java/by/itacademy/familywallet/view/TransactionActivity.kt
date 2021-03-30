package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.data.BANK_MINUS
import by.itacademy.familywallet.data.BANK_PLUS
import by.itacademy.familywallet.data.CARD
import by.itacademy.familywallet.data.CASH
import by.itacademy.familywallet.data.CATEGORIES
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
import org.koin.android.ext.android.inject
import java.util.*

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private var type: String? = null
    private var category: String? = null
    private val repo by inject<DataRepository>()
    private val dialog by inject<Dialogs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        if (intent != null) {
            type = intent.getStringExtra(TRANSACTION_TYPE)
            category = intent.getStringExtra(CATEGORIES)
        }
        if (type != null) {
            initViews()
        }
    }

    private fun createDialog(moneyType: String?) {
        val transactionModel = UIModel.TransactionModel(
            uid = UserUtils.getUsersUid(),
            type = type,
            category = category,
            currency = binding.currencySpinner.selectedItem.toString(),
            moneyType = moneyType,
            value = binding.transactionValue.text.toString().toDouble(),
            date = binding.date.date
        )
        val dialog = TransactionDialog(repo, transactionModel)
        dialog.show(supportFragmentManager, "dialog")
    }

    private fun initViews() {
        val preparation = App().viewPreparation
        with(binding) {
            if (type == BANK) {
                preparation.prepareBankViews(binding, this@TransactionActivity)
            } else {
                transactionCategoryTitle.text = category
                preparation.prepareView(transactionCategoryTitle, type!!)
                preparation.prepareView(transactionValue, type!!)
                preparation.prepareView(currencySpinner, type!!)
                preparation.prepareView(cashButton, type!!)
                preparation.prepareView(cardButton, type!!)
            }

            date.setOnDateChangeListener { view, year, month, dayOfMonth ->
                view.date = GregorianCalendar(year, month, dayOfMonth).timeInMillis
            }
            cashButton.setOnClickListener {
                if (type != null && !binding.transactionValue.text.isNullOrEmpty()) {
                    if (type == BANK) {
                        createDialog(BANK_PLUS)
                    } else {
                        createDialog(CASH)
                    }
                } else {
                    dialog.createNegativeDialog(this@TransactionActivity, getString(R.string.alert_negative_message_transaction))
                }
            }

            cardButton.setOnClickListener {
                if (type != null && !binding.transactionValue.text.isNullOrEmpty()) {
                    if (type == BANK) {
                        createDialog(BANK_MINUS)
                    } else {
                        createDialog(CARD)
                    }
                } else {
                    dialog.createNegativeDialog(this@TransactionActivity,getString(R.string.alert_negative_message_transaction))
                }
            }
        }
    }

    companion object {
        fun start(context: Context?, type: String?, category: String?) =
            Intent(context, TransactionActivity::class.java).apply {
                putExtra(TRANSACTION_TYPE, type)
                putExtra(CATEGORIES, category)
            }
    }
}