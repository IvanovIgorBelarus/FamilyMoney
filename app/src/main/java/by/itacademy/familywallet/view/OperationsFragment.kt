package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.viewmodel.BaseViewModel
import by.itacademy.familywallet.viewmodel.OperationsViewModel
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
        binding.adapterRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fragmentAdapter
        }
        updateAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }

    companion object {
        @JvmStatic
        fun newInstance() = OperationsFragment()
    }
}