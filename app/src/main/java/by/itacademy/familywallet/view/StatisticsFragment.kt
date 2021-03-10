package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.viewmodel.StatisticViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class StatisticsFragment : Fragment() {
    private val statisticViewModel by viewModel<StatisticViewModel>()
    private lateinit var binding: FragmentStatisticsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_statistics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)
        statisticViewModel.liveData.observe(this, Observer { list -> binding.te.text = "${list.size}" })
    }

    override fun onResume() {
        super.onResume()
        statisticViewModel.getAllTransActions()
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatisticsFragment()
    }
}