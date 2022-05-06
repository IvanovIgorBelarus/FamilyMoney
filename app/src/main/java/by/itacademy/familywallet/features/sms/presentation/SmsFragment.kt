package by.itacademy.familywallet.features.sms.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.wrappers.DeleteSmsWrapper
import by.itacademy.familywallet.common.wrappers.SettingsChangeWrapper
import by.itacademy.familywallet.core.others.CARD
import by.itacademy.familywallet.core.others.EXPENSES
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.core.others.ItemClickListener
import by.itacademy.familywallet.core.others.ItemOnLongClickListener
import by.itacademy.familywallet.core.others.BaseFragment
import by.itacademy.familywallet.features.transaction.presentation.TransactionFragment
import by.itacademy.familywallet.features.sms.presentation.view_model.SmsViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SmsFragment : BaseFragment<FragmentAdapter, SmsViewModel>(R.layout.fragment_statistics), ItemClickListener {
    private lateinit var binding: FragmentStatisticsBinding

    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(this as ItemClickListener, this as ItemOnLongClickListener) }
    override val viewModel by viewModel<SmsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)
        showActionBar(false)
        initViews()
    }

    override fun onClick(item: Any?) {
        addFragment(
            TransactionFragment.newInstance(
                UIModel.TransactionModel(
                    type = EXPENSES,
                    category = "",
                    currency = (item as UIModel.SmsModel).currency,
                    moneyType = CARD,
                    date = item.date,
                    value = item.value,
                    id = item.id
                ),
                isUpdate = false,
                isSms = true
            )
        )
    }

    private fun initViews() {
        with(binding) {
            adapterRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = fragmentAdapter
            }
            with(titleTextView) {
                visibility = View.VISIBLE
                text = getString(R.string.sms_title)
            }
        }
    }

    override fun checkDescribeVisibility(isShowing: Boolean) {
        if (isShowing) {
            binding.titleEmptyAdapter.visibility = View.VISIBLE
        } else {
            binding.titleEmptyAdapter.visibility = View.GONE
        }
    }

    override fun listenBus(wrapper: Any) {
        super.listenBus(wrapper)
        when(wrapper){
            is DeleteSmsWrapper -> viewModel.getData(true)
            is SettingsChangeWrapper -> viewModel.getData()
        }
    }

    companion object {
        fun newInstance() = SmsFragment()
    }
}