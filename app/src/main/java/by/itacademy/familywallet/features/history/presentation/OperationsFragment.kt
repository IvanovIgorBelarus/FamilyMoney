package by.itacademy.familywallet.features.history.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.wrappers.SettingsChangeWrapper
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.core.others.ItemOnLongClickListener
import by.itacademy.familywallet.core.others.BaseFragment
import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.features.history.view_model.OperationsViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OperationsFragment : BaseFragment<FragmentAdapter, BaseViewModel>(R.layout.fragment_statistics) {

    private lateinit var binding: FragmentStatisticsBinding

    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, this as ItemOnLongClickListener) }
    override val viewModel by viewModel<OperationsViewModel>()

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
            titleEmptyAdapter.text = getString(R.string.operation_title_empty)
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
        if (wrapper is SettingsChangeWrapper){
            viewModel.getData()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OperationsFragment()
    }
}