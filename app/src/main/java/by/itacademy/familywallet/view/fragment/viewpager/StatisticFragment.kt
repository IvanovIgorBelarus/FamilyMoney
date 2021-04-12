package by.itacademy.familywallet.view.fragment.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.viewmodel.StatisticViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StatisticFragment : BaseFragment<FragmentAdapter, StatisticViewModel>(R.layout.fragment_statistics) {
    private lateinit var binding: FragmentStatisticsBinding

    override val viewModel by viewModel<StatisticViewModel>()
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, null) }

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
        fun newInstance() = StatisticFragment()
    }
}