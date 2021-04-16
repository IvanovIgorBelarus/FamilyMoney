package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.CARD
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.viewmodel.SmsViewModel
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
                    value = item.value
                ), false
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

    companion object {
        fun newInstance() = SmsFragment()
    }
}