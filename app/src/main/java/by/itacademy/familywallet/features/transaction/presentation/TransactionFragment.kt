package by.itacademy.familywallet.features.transaction.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.wrappers.CategoryChangeWrapper
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.core.others.BANK
import by.itacademy.familywallet.core.others.BANK_MINUS
import by.itacademy.familywallet.core.others.BANK_PLUS
import by.itacademy.familywallet.core.others.BYN
import by.itacademy.familywallet.core.others.BaseFragment
import by.itacademy.familywallet.core.others.CARD
import by.itacademy.familywallet.core.others.CASH
import by.itacademy.familywallet.core.others.CATEGORY
import by.itacademy.familywallet.core.others.CURRENCY
import by.itacademy.familywallet.core.others.DATE
import by.itacademy.familywallet.core.others.EUR
import by.itacademy.familywallet.core.others.ID
import by.itacademy.familywallet.core.others.INCOMES
import by.itacademy.familywallet.core.others.MONEY_TYPE
import by.itacademy.familywallet.core.others.RUB
import by.itacademy.familywallet.core.others.TRANSACTION_TYPE
import by.itacademy.familywallet.core.others.UID
import by.itacademy.familywallet.core.others.USD
import by.itacademy.familywallet.core.others.VALUE
import by.itacademy.familywallet.databinding.FragmentTransactionBinding
import by.itacademy.familywallet.features.transaction.view_model.TransactionViewModel
import by.itacademy.familywallet.features.transactions_list.presentation.TypeTransactionFragment
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.UserUtils
import org.koin.android.ext.android.inject
import java.util.*

class TransactionFragment : BaseFragment<FragmentAdapter, TransactionViewModel>(R.layout.fragment_transaction) {
    private lateinit var binding: FragmentTransactionBinding
    private var item: UIModel.TransactionModel? = null
    private var isUpdate = false
    private var isSms = false

    override val viewModel by inject<TransactionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionBinding.bind(view)
        showActionBar(false)
        if (item?.type != null) {
            initViews()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initItem()
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
                transactionCategoryTitle.text = getString(R.string.bank)
            } else {
                with(transactionCategoryTitle) {
                    setOnClickListener { addFragment(TypeTransactionFragment.newInstance(item?.type!!, true)) }
                    text = if (!item?.category.isNullOrEmpty()) item?.category else getString(R.string.category_null_title)
                }
            }
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
                        hideKeyBoard()
                        binding.currencyLayout.visibility = View.VISIBLE
                        binding.currencyLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down))
//                        viewModel.getCurrency(binding.currencySpinner.selectedItem.toString())
                        viewModel.liveDataCurrency.observe(this@TransactionFragment, { transactionCurrency.setText(it.toString()) })
                    } else {
                        createDialog(CARD)
                    }
                } else {
                    dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_transaction))
                }
            }

            cancelButton.setOnClickListener { createDialog(BANK_MINUS) }

            confirmButton.setOnClickListener {
                if (currency.text.isNotEmpty()) {
                    doBankTransaction()
                    onBack()
                } else {
                    Toast.makeText(context, "Введите курс обмена валют!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createDialog(moneyType: String?) {
        val transactionModel = UIModel.TransactionModel(
            uid = UserUtils.getUsersUid(),
            type = item?.type,
            category = binding.transactionCategoryTitle.text.toString(),
            currency = binding.currencySpinner.selectedItem.toString(),
            moneyType = moneyType,
            value = binding.transactionValue.text.toString().toDouble(),
            date = binding.date.date,
            id = item?.id
        )
        if (transactionModel.category == getString(R.string.category_null_title)) {
            Toast.makeText(context, "Выберите категорию!!!", Toast.LENGTH_SHORT).show()
        } else {
            if (item?.currency.isNullOrEmpty()) {
                dialog.createTransactionDialog(this, transactionModel)
            } else {
                dialog.createTransactionDialog(this, transactionModel, isUpdate, isSms)
            }
        }
    }

    private fun doBankTransaction() {
        val transactionValue = binding.transactionValue.text.toString().toDouble()
        val currency = binding.transactionCurrency.text.toString().toDouble()
        val transactionModel = UIModel.TransactionModel(
            uid = UserUtils.getUsersUid(),
            type = INCOMES,
            category = INCOMES,
            currency = binding.currencySpinner.selectedItem.toString(),
            moneyType = CASH,
            value = transactionValue,
            date = binding.date.date
        )
        viewModel.doTransaction(transactionModel, currency)
    }

    override fun listenBus(wrapper: Any) {
        super.listenBus(wrapper)
        when (wrapper) {
            is CategoryChangeWrapper -> {
                binding.transactionCategoryTitle.text = wrapper.category
                item?.category = wrapper.category
            }
        }
    }

    companion object {
        fun newInstance(type: String?, category: String?) = TransactionFragment().apply {
            arguments = Bundle().apply {
                putString(TRANSACTION_TYPE, type)
                putString(CATEGORY, category)
            }
        }

        fun newInstance(item: UIModel.TransactionModel, isUpdate: Boolean = true, isSms: Boolean = false) = TransactionFragment().apply {
            this.isUpdate = isUpdate
            this.isSms = isSms
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

