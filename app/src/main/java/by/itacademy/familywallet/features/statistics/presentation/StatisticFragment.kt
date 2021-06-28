package by.itacademy.familywallet.features.statistics.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.core.others.ItemClickListener
import by.itacademy.familywallet.core.others.BaseFragment
import by.itacademy.familywallet.features.operations.presantation.CategoryOperationFragment
import by.itacademy.familywallet.features.statistics.view_model.StatisticViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StatisticFragment : BaseFragment<FragmentAdapter, StatisticViewModel>(R.layout.fragment_statistics), ItemClickListener {
    private lateinit var binding: FragmentStatisticsBinding

    override val viewModel by viewModel<StatisticViewModel>()
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(this as ItemClickListener, null) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            adapterRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = fragmentAdapter
            }
            titleEmptyAdapter.text = getString(R.string.statistic_title_empty)
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
        fun newInstance() = StatisticFragment()
    }

    override fun onClick(item: Any?) {
        addFragment(CategoryOperationFragment.newInstance((item as UIModel.StatisticModel).category!!, item.type!!))
    }
}