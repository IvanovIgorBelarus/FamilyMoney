package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.data.BANK_MINUS
import by.itacademy.familywallet.data.BANK_PLUS
import by.itacademy.familywallet.data.BYN
import by.itacademy.familywallet.data.CARD
import by.itacademy.familywallet.data.CASH
import by.itacademy.familywallet.data.CATEGORY
import by.itacademy.familywallet.data.CURRENCY
import by.itacademy.familywallet.data.DATE
import by.itacademy.familywallet.data.EUR
import by.itacademy.familywallet.data.ID
import by.itacademy.familywallet.data.MONEY_TYPE
import by.itacademy.familywallet.data.RUB
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.data.UID
import by.itacademy.familywallet.data.USD
import by.itacademy.familywallet.data.VALUE
import by.itacademy.familywallet.databinding.FragmentTransactionBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.view.activity.FragmentsActivity
import by.itacademy.familywallet.viewmodel.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class TransactionFragment : BaseFragment<FragmentAdapter, BaseViewModel>(R.layout.fragment_transaction) {
    private lateinit var binding: FragmentTransactionBinding
    private var item: UIModel.TransactionModel? = null

    override val viewModel by inject<BaseViewModel>()
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, null) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionBinding.bind(view)
        showActionBar(false)
        initItem()
        if (item?.type != null) {
            initViews()
        }
    }

    private fun initItem() {
        item = UIModel.TransactionModel(
            id = arguments?.getString(ID),
            uid = arguments?.getString(UID),
            type = arguments?.getString(TRANSACTION_TYPE),
            category = arguments?.getString(CATEGORY),
            currency = arguments?.getString(CURRENCY),
            moneyType = arguments?.getString(MONEY_TYPE),
            date = arguments?.getLong(DATE),
            value = arguments?.getDouble(VALUE)
        )
    }

    private fun initViews() {
        with(binding) {
            if (item?.type == BANK) {
                cashButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_arrow_upward_24, context?.theme), null, null, null)
                cashButton.setText(R.string.add)
                cardButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_arrow_downward_24, context?.theme), null, null, null)
                cardButton.setText(R.string.out)
            }

            transactionCategoryTitle.text = item?.category
            if (item?.value != 0.0) {
                transactionValue.setText(item?.value.toString())
                date.date = item?.date!!
            }

            with(currencySpinner) {
                val currencyArray = arrayOf(BYN, USD, EUR, RUB)
                adapter = ArrayAdapter(context, R.layout.primary_spinner_item, currencyArray)
            }

            date.setOnDateChangeListener { view, year, month, dayOfMonth ->
                view.date = GregorianCalendar(year, month, dayOfMonth).timeInMillis
            }
            cashButton.setOnClickListener {
                if (item?.type != null && !binding.transactionValue.text.isNullOrEmpty()) {
                    if (item?.type == BANK) {
                        createDialog(BANK_PLUS)
                    } else {
                        createDialog(CASH)
                    }
                } else {
                    dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_transaction))
                }
            }

            cardButton.setOnClickListener {
                if (item?.type != null && !binding.transactionValue.text.isNullOrEmpty()) {
                    if (item?.type == BANK) {
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
        if (item?.currency.isNullOrEmpty()) {
            val transactionModel = UIModel.TransactionModel(
                uid = UserUtils.getUsersUid(),
                type = item?.type,
                category = item?.category,
                currency = binding.currencySpinner.selectedItem.toString(),
                moneyType = moneyType,
                value = binding.transactionValue.text.toString().toDouble(),
                date = binding.date.date)
            dialog.createTransactionDialog(this, transactionModel)
        } else {
            with(item!!){
                currency = binding.currencySpinner.selectedItem.toString()
                this.moneyType = moneyType
                value = binding.transactionValue.text.toString().toDouble()
                date = binding.date.date
            }
            dialog.createTransactionDialog(this, item!!, true)
        }
    }

    companion object {
        fun newInstance(type: String?, category: String?) = TransactionFragment().apply {
            arguments = Bundle().apply {
                putString(TRANSACTION_TYPE, type)
                putString(CATEGORY, category)
            }
        }

        fun newInstance(item: UIModel.TransactionModel) = TransactionFragment().apply {
            arguments = Bundle().apply {
                with(item) {
                    putString(ID, id)
                    putString(UID, uid)
                    putString(TRANSACTION_TYPE, type)
                    putString(CATEGORY, category)
                    putString(CURRENCY, currency)
                    putString(MONEY_TYPE, moneyType)
                    putLong(DATE, date!!)
                    putDouble(VALUE, value!!)
                }
            }
        }
    }
}

