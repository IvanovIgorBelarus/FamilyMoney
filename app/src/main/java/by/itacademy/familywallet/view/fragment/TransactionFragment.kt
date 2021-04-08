package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.data.BANK_MINUS
import by.itacademy.familywallet.data.BANK_PLUS
import by.itacademy.familywallet.data.BYN
import by.itacademy.familywallet.data.CARD
import by.itacademy.familywallet.data.CASH
import by.itacademy.familywallet.data.CATEGORIES
import by.itacademy.familywallet.data.EUR
import by.itacademy.familywallet.data.RUB
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.data.USD
import by.itacademy.familywallet.databinding.FragmentTransactionBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.view.activity.FragmentsActivity
import org.koin.android.ext.android.inject
import java.util.*

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private var type: String? = null
    private var category: String? = null
    private val dialog by inject<Dialogs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionBinding.bind(view)
        (activity as FragmentsActivity).supportActionBar?.hide()
        type = arguments?.getString(TRANSACTION_TYPE)
        category = arguments?.getString(CATEGORIES)
        if (type != null) {
            initViews()
        }
    }

    private fun initViews() {
        with(binding) {
            if (type == BANK) {
                cashButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_arrow_upward_24, context?.theme), null, null, null)
                cashButton.setText(R.string.add)
                cardButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_arrow_downward_24, context?.theme), null, null, null)
                cardButton.setText(R.string.out)
            } else {
                transactionCategoryTitle.text = category
            }
            with(currencySpinner) {
                val currencyArray = arrayOf(BYN, USD, EUR, RUB)
                adapter = ArrayAdapter(context, R.layout.primary_spinner_item, currencyArray)
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
                    dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_transaction))
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
                    dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_transaction))
                }
            }
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
        dialog.createTransactionDialog(this, transactionModel)
    }

    fun closeFragment() {
        (activity as FragmentsActivity).onBackPressed()
    }

    companion object {
        fun newInstance(type: String?, category: String?) = TransactionFragment().apply {
            arguments = Bundle().apply {
                putString(TRANSACTION_TYPE, type)
                putString(CATEGORIES, category)
            }
        }
    }
}

